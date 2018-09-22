package pl.consdata.ico.sqcompanion.violation.user.diff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.users.UsersService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserViolationDiffSyncService {

    private final UsersService usersService;
    private final UserViolationDiffRepository repository;
    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;

    public UserViolationDiffSyncService(
            final UsersService usersService,
            final UserViolationDiffRepository repository,
            final SonarQubeFacade sonarQubeFacade,
            final RepositoryService repositoryService,
            final AppConfig appConfig) {
        this.usersService = usersService;
        this.repository = repository;
        this.sonarQubeFacade = sonarQubeFacade;
        this.repositoryService = repositoryService;
        this.appConfig = appConfig;
    }

    public void sync() {
        appConfig.getServers().forEach(this::syncServer);
    }

    private void syncServer(final ServerDefinition server) {
        final List<SonarQubeUser> users = usersService.users(server.getId());
        final List<Project> projects = repositoryService.getRootGroup().getAllProjects();
        users.forEach(user -> syncUser(user, projects));
    }

    private void syncUser(final SonarQubeUser user, final List<Project> projects) {
        projects.forEach(project -> syncUserProject(project, user));
    }

    private void syncUserProject(final Project project, final SonarQubeUser user) {
        final LocalDate today = LocalDate.now();
        final LocalDate firstRequiredSyncDate = lastMeasure(project, user)
                .map(m -> m.getDate())
                .orElse(LocalDate.now().minusDays(30));
        if (!firstRequiredSyncDate.isBefore(today.minusDays(1))) {
            log.debug("All historic analysis already synchronized");
            return;
        }

        log.info("Syncing user diff violations [userId={}, project={}]", user.getUserId(), project.getKey());

        final LocalDate syncDate = syncStartDate(firstRequiredSyncDate);
        final Map<LocalDate, List<SonarQubeIssue>> userIssues = userIssuesAfterDate(project, user, syncDate);
        final List<UserProjectViolationDiffHistoryEntry> entries = new ArrayList<>();
        for (LocalDate date = syncDate; date.isBefore(today); date = date.plusDays(1)) {
            log.trace("Syncing user history project history [user={}, project={}, date={}]", user.getUserId(), project.getKey(), date);
            final String entryByDayId = UserProjectViolationDiffHistoryEntry.combineId(project.getServerId(), user.getUserId(), project.getKey(), date);
            if (userIssues.containsKey(date)) {
                entries.add(historyEntryForIssues(project, user, userIssues, date, entryByDayId));
            } else {
                entries.add(
                        emptyUserProjectEntry(project, user)
                                .id(entryByDayId)
                                .date(date)
                                .build()
                );
            }
        }
        if (!entries.isEmpty()) {
            repository.saveAll(entries);
        }
    }

    private UserProjectViolationDiffHistoryEntry historyEntryForIssues(Project project, SonarQubeUser user, Map<LocalDate, List<SonarQubeIssue>> userIssues, LocalDate date, String entryByDayId) {
        final UserProjectViolationDiffHistoryEntry entry = emptyUserProjectEntry(project, user)
                .id(entryByDayId)
                .date(date)
                .issues(mapIssues(userIssues.get(date)))
                .build();
        userIssues.get(date).forEach(updateEntryWithIssueSeverity(entry));
        return entry;
    }

    private Consumer<SonarQubeIssue> updateEntryWithIssueSeverity(UserProjectViolationDiffHistoryEntry entry) {
        return issue -> {
            switch (issue.getSeverity()) {
                case BLOCKER:
                    entry.setBlockers(entry.getBlockers() + 1);
                    break;
                case CRITICAL:
                    entry.setCriticals(entry.getCriticals() + 1);
                    break;
                case MAJOR:
                    entry.setMajors(entry.getMajors() + 1);
                    break;
                case MINOR:
                    entry.setMinors(entry.getMinors() + 1);
                    break;
                case INFO:
                    entry.setInfos(entry.getInfos() + 1);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown issue severity [" + issue.getSeverity() + "]");
            }
        };
    }

    private String mapIssues(final List<SonarQubeIssue> userIssues) {
        return userIssues
                .stream()
                .map(issue -> String.format("%s:%s", issue.getSeverity(), issue.getKey()))
                .collect(Collectors.joining(","));
    }

    private LocalDate syncStartDate(final LocalDate firstRequiredSyncDate) {
        // due to SonarQube housekeeping issues older than 30 day are removed
        final LocalDate minSyncDate = LocalDate.now().minusDays(30);
        return firstRequiredSyncDate.isAfter(minSyncDate) ? firstRequiredSyncDate : minSyncDate;
    }

    private UserProjectViolationDiffHistoryEntry.UserProjectViolationDiffHistoryEntryBuilder emptyUserProjectEntry(final Project project, final SonarQubeUser user) {
        return UserProjectViolationDiffHistoryEntry.builder()
                .userId(user.getUserId())
                .projectKey(project.getKey())
                .serverId(project.getServerId())
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0);
    }

    private Map<LocalDate, List<SonarQubeIssue>> userIssuesAfterDate(final Project project, final SonarQubeUser user, final LocalDate syncDate) {
        final IssueFilter userIssuesFilter = IssueFilter.builder()
                .componentKey(project.getKey())
                .author(user.getUserId())
                .createdAfter(syncDate)
                .build();
        return sonarQubeFacade.issues(project.getServerId(), userIssuesFilter)
                .stream()
                .collect(Collectors.groupingBy(SonarQubeIssue::getCreationDay));
    }

    private Optional<UserProjectViolationDiffHistoryEntry> lastMeasure(final Project project, final SonarQubeUser user) {
        return repository.findFirstByUserIdAndProjectKeyOrderByDateDesc(user.getUserId(), project.getKey());
    }

}
