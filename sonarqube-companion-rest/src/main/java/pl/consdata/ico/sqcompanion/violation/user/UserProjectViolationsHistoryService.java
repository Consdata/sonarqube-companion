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
import pl.consdata.ico.sqcompanion.users.UsersService;

import java.time.LocalDate;
import java.util.List;

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
        projects.forEach(project -> syncUserProject(project, user));
    }

    private void syncUserProject(final Project project, final SonarQubeUser user) {
        log.info("Syncing user violations [userId={}, project={}]", user.getUserId(), project.getKey());

        final LocalDate today = LocalDate.now();
        final List<SonarQubeIssue> issues = sonarQubeFacade.issues(
                project.getServerId(),
                IssueFilter.builder()
                        .componentKey(project.getKey())
                        .author(user.getUserId())
                        .resolved(false)
                        .build()
        );
        repository.save(
                UserProjectHistoryEntryEntity.builder()
                        .id(UserProjectHistoryEntryEntity.combineId(project.getServerId(), user.getUserId(), project.getKey(), today))
                        .serverId(project.getServerId())
                        .userId(user.getUserId())
                        .projectKey(project.getKey())
                        .date(today)
                        .blockers(countIssueByType(issues, SonarQubeIssueSeverity.BLOCKER))
                        .criticals(countIssueByType(issues, SonarQubeIssueSeverity.CRITICAL))
                        .majors(countIssueByType(issues, SonarQubeIssueSeverity.MAJOR))
                        .minors(countIssueByType(issues, SonarQubeIssueSeverity.MINOR))
                        .infos(countIssueByType(issues, SonarQubeIssueSeverity.INFO))
                        .build()
        );
    }

    private int countIssueByType(List<SonarQubeIssue> issues, SonarQubeIssueSeverity severity) {
        return Math.toIntExact(issues.stream().filter(issue -> issue.getSeverity() == severity).count());
    }

}
