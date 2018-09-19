package pl.consdata.ico.sqcompanion.violation.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilterSortField;
import pl.consdata.ico.sqcompanion.users.UsersService;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserProjectViolationsHistoryService {

    private final UsersService usersService;
    private final UserProjectHistoryRepository repository;
    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;

    public UserProjectViolationsHistoryService(
            final UsersService usersService,
            final UserProjectHistoryRepository repository,
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
        repositoryService
                .getRootGroup()
                .accept(
                        gr -> users.forEach(user -> syncUser(user, gr.getProjects()))
                );
    }

    private void syncUser(final SonarQubeUser user, final List<Project> projects) {
        log.info("Syncing user violations [userId={}]", user.getUserId());
        projects.forEach(project -> syncUserProject(project, user));
    }

    private void syncUserProject(final Project project, final SonarQubeUser user) {
        log.debug("Syncing user violations [userId={}, project={}]", user.getUserId(), project.getKey());

        final Optional<LocalDate> firstRequiredSyncDate = firstSyncDate(project, user);
        if (!firstRequiredSyncDate.isPresent()) {
            log.debug(
                    "Can't get first date to start syncing project for user, skipping [project={}, user={}]",
                    project.getKey(),
                    user.getUserId()
            );
            return;
        }

        final LocalDate syncDate = syncStartDate(firstRequiredSyncDate);
        final Map<LocalDate, List<SonarQubeIssue>> userIssues = userIssuesByDate(project, user, syncDate);
        UserProjectHistoryEntryEntity lastEntry = emptyUserProjectEntry(project, user);
        for (LocalDate date = syncDate; !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
            log.trace("Syncing user history project history [user={}, project={}, date={}]", user.getUserId(), project.getKey(), date);
            final String entryByDayId = UserProjectHistoryEntryEntity.combineId(project.getServerId(), user.getUserId(), project.getKey(), date);
            final UserProjectHistoryEntryEntity.UserProjectHistoryEntryEntityBuilder entryBuilder = lastEntry.toBuilder()
                    .id(entryByDayId)
                    .date(date);
            if (userIssues.containsKey(date)) {
                entryBuilder
                        .blockers(countIssueByType(userIssues.get(date), SonarQubeIssueSeverity.BLOCKER))
                        .criticals(countIssueByType(userIssues.get(date), SonarQubeIssueSeverity.CRITICAL))
                        .majors(countIssueByType(userIssues.get(date), SonarQubeIssueSeverity.MAJOR))
                        .minors(countIssueByType(userIssues.get(date), SonarQubeIssueSeverity.MINOR))
                        .infos(countIssueByType(userIssues.get(date), SonarQubeIssueSeverity.INFO));
            }
            lastEntry = entryBuilder.build();
            repository.save(lastEntry);
        }

    }

    private LocalDate syncStartDate(Optional<LocalDate> firstRequiredSyncDate) {
        // due to SonarQube housekeeping issues older than 30 day are removed
        final LocalDate minSyncDate = LocalDate.now().minusDays(30);
        return firstRequiredSyncDate.get().isAfter(minSyncDate) ? firstRequiredSyncDate.get() : minSyncDate;
    }

    private UserProjectHistoryEntryEntity emptyUserProjectEntry(Project project, SonarQubeUser user) {
        return UserProjectHistoryEntryEntity.builder()
                    .userId(user.getUserId())
                    .projectKey(project.getKey())
                    .serverId(project.getServerId())
                    .blockers(0)
                    .criticals(0)
                    .majors(0)
                    .minors(0)
                    .infos(0)
                    .build();
    }

    private Map<LocalDate, List<SonarQubeIssue>> userIssuesByDate(Project project, SonarQubeUser user, LocalDate syncDate) {
        final IssueFilter userIssuesFilter = IssueFilter.builder()
                .componentKey(project.getKey())
                .author(user.getUserId())
                .createdAfter(syncDate)
                .build();
        return sonarQubeFacade.issues(project.getServerId(), userIssuesFilter)
                .stream()
                .collect(Collectors.groupingBy(SonarQubeIssue::getCreationDay));
    }

    private int countIssueByType(List<SonarQubeIssue> issues, SonarQubeIssueSeverity severity) {
        return Math.toIntExact(issues.stream().filter(issue -> issue.getSeverity() == severity).count());
    }

    private Optional<LocalDate> firstSyncDate(final Project project, final SonarQubeUser user) {
        final Optional<LocalDate> lastMeasureDate = lastMeasure(project, user).map(m -> m.getDate());
        if (lastMeasureDate.isPresent()) {
            return lastMeasureDate;
        } else {
            return firstProjectIssue(project)
                    .map(i -> i.getCreationDate())
                    .map(LocalDateUtil::asLocalDate);
        }
    }

    private Optional<UserProjectHistoryEntryEntity> lastMeasure(Project project, SonarQubeUser user) {
        return repository.findFirstByUserIdAndProjectKeyOrderByDateDesc(user.getUserId(), project.getKey());
    }

    private Optional<SonarQubeIssue> firstProjectIssue(final Project project) {
        final List<SonarQubeIssue> issue = sonarQubeFacade.issues(
                project.getServerId(),
                IssueFilter.builder()
                        .componentKey(project.getKey())
                        .limit(1)
                        .sort(IssueFilterSortField.CREATION_DATE)
                        .asc(true)
                        .build()
        );
        if (issue.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(issue.get(0));
        }
    }

}
