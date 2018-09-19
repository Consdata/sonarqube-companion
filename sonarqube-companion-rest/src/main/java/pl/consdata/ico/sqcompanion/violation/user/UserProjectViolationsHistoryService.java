package pl.consdata.ico.sqcompanion.violation.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.project.ProjectEntity;
import pl.consdata.ico.sqcompanion.project.ProjectService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilterSortField;
import pl.consdata.ico.sqcompanion.users.UsersService;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserProjectViolationsHistoryService {

    private final UsersService usersService;
    private final UserProjectHistoryRepository repository;
    private ProjectService projectService;
    private SonarQubeFacade sonarQubeFacade;
    private AppConfig appConfig;

    public UserProjectViolationsHistoryService(
            final UsersService usersService,
            final UserProjectHistoryRepository repository,
            final ProjectService projectService,
            final SonarQubeFacade sonarQubeFacade,
            final AppConfig appConfig) {
        this.usersService = usersService;
        this.repository = repository;
        this.projectService = projectService;
        this.sonarQubeFacade = sonarQubeFacade;
        this.appConfig = appConfig;
    }

    @Transactional
    public void sync() {
        appConfig.getServers().forEach(this::syncServer);
    }

    private void syncServer(final ServerDefinition server) {
        final List<ProjectEntity> projects = projectService.getProjects(server.getId());
        usersService.users(server.getId()).forEach(user -> syncUser(projects, user));
        // TODO: remove old entries for non existing users
    }

    private void syncUser(final List<ProjectEntity> projects, final SonarQubeUser user) {
        projects.forEach(project -> syncUserProject(project, user));
    }

    private void syncUserProject(final ProjectEntity project, final SonarQubeUser user) {
        log.info("Syncing user violations [userId={}, project={}]", user.getUserId(), project.getKey());

        final Optional<LocalDate> firstSyncDate = firstSyncDate(project, user);
        if (!firstSyncDate.isPresent()) {
            log.info(
                    "Can't get first date to start syncing project for user [project={}, user={}",
                    project.getKey(),
                    user.getUserId()
            );
            return;
        }

        final List<UserProjectHistoryEntryEntity> entities = new ArrayList<>();
        UserProjectHistoryEntryEntity lastEntry = UserProjectHistoryEntryEntity.builder()
                .userId(user.getUserId())
                .projectKey(project.getKey())
                .serverId(project.getServerId())
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0)
                .build();
        for (LocalDate date = firstSyncDate.get(); !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
            final List<SonarQubeIssue> issues = sonarQubeFacade.issues(
                    project.getServerId(),
                    IssueFilter.builder()
                            .componentKey(project.getKey())
                            .createdAfter(date)
                            .createdBefore(date)
                            .author(user.getUserId())
                            .build()
            );
            final String entryByDayId = UserProjectHistoryEntryEntity.combineId(project.getServerId(), user.getUserId(), project.getKey(), date);
            final UserProjectHistoryEntryEntity.UserProjectHistoryEntryEntityBuilder entryBuilder = lastEntry.toBuilder()
                    .id(entryByDayId)
                    .date(date);
            if (issues.isEmpty()) {
                lastEntry = entryBuilder.build();
            } else {
                lastEntry = entryBuilder
                        .blockers(countIssueByType(issues, SonarQubeIssueSeverity.BLOCKER))
                        .criticals(countIssueByType(issues, SonarQubeIssueSeverity.CRITICAL))
                        .majors(countIssueByType(issues, SonarQubeIssueSeverity.MAJOR))
                        .minors(countIssueByType(issues, SonarQubeIssueSeverity.MINOR))
                        .infos(countIssueByType(issues, SonarQubeIssueSeverity.INFO))
                        .build();
            }
            entities.add(lastEntry);
        }

        repository.saveAll(entities);
    }

    private int countIssueByType(List<SonarQubeIssue> issues, SonarQubeIssueSeverity severity) {
        return Math.toIntExact(issues.stream().filter(issue -> issue.getSeverity() == severity).count());
    }

    private Optional<LocalDate> firstSyncDate(final ProjectEntity project, final SonarQubeUser user) {
        final Optional<LocalDate> lastMeasureDate = lastMeasure(project, user).map(m -> m.getDate());
        if (lastMeasureDate.isPresent()) {
            return lastMeasureDate;
        } else {
            return firstProjectIssue(project)
                    .map(i -> i.getCreationDate())
                    .map(LocalDateUtil::asLocalDate);
        }
    }

    private Optional<UserProjectHistoryEntryEntity> lastMeasure(ProjectEntity project, SonarQubeUser user) {
        return repository.findByUserIdAndProjectKeyOrderByDateDesc(project.getKey(), user.getUserId());
    }

    private Optional<SonarQubeIssue> firstProjectIssue(final ProjectEntity project) {
        final List<SonarQubeIssue> issuse = sonarQubeFacade.issues(
                project.getServerId(),
                IssueFilter.builder()
                        .componentKey(project.getKey())
                        .limit(1)
                        .sort(IssueFilterSortField.CREATION_DATE)
                        .asc(true)
                        .build()
        );
        if (issuse.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(issuse.get(0));
        }
    }

}
