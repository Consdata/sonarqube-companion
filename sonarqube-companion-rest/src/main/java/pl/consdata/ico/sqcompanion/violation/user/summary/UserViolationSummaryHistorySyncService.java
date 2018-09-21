package pl.consdata.ico.sqcompanion.violation.user.summary;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssuesFacet;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssuesFacets;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilterFacet;
import pl.consdata.ico.sqcompanion.users.UsersService;
import pl.consdata.ico.sqcompanion.violation.ViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryEntryEntity;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class UserViolationSummaryHistorySyncService {

    private final UsersService usersService;
    private final UserViolationHistoryRepository repository;
    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;
    private final Timer syncUserProjectTimer;

    public UserViolationSummaryHistorySyncService(
            final UsersService usersService,
            final UserViolationHistoryRepository repository,
            final SonarQubeFacade sonarQubeFacade,
            final RepositoryService repositoryService,
            final AppConfig appConfig,
            final MeterRegistry meterRegistry) {
        this.usersService = usersService;
        this.repository = repository;
        this.sonarQubeFacade = sonarQubeFacade;
        this.repositoryService = repositoryService;
        this.appConfig = appConfig;
        this.syncUserProjectTimer = meterRegistry.timer("UserProjectViolationsSummaryHistoryService.syncUserProject");
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
        log.info("Syncing user summary violations [userId={}, projects={}]", user.getUserId(), projects.size());
        for (int projectIdx = 0; projectIdx < projects.size(); ++projectIdx) {
            final Project project = projects.get(projectIdx);
            log.debug("Syncing user summary violations [userId={}, project={}]", user.getUserId(), project.getKey());
            if (projectIdx > 0 && projectIdx % 20 == 0) {
                log.info("...syncing user summary projects [userId={}, progress={}/{}]", user.getUserId(), projectIdx, projects.size());
            }
            syncUserProject(project, user);
        }
    }

    private void syncUserProject(final Project project, final SonarQubeUser user) {
        syncUserProjectTimer.record(() -> {
            final SonarQubeIssuesFacets userIssuesFacets = sonarQubeFacade.issuesFacet(
                    project.getServerId(),
                    IssueFilter.builder()
                            .componentKey(project.getKey())
                            .author(user.getUserId())
                            .resolved(false)
                            .facet(IssueFilterFacet.SEVERITIES)
                            .build()
            );
            final SonarQubeIssuesFacet severities = userIssuesFacets
                    .getFacet(IssueFilterFacet.SEVERITIES)
                    .orElseThrow(() -> new IllegalStateException("Expected facet not returned from SonarQube!"));
            final LocalDate today = LocalDate.now();
            repository.save(
                    UserProjectSummaryViolationHistoryEntry.builder()
                            .id(
                                    UserProjectSummaryViolationHistoryEntry.combineId(
                                            project.getServerId(),
                                            user.getUserId(),
                                            project.getKey(),
                                            today
                                    )
                            )
                            .date(today)
                            .serverId(project.getServerId())
                            .userId(user.getUserId())
                            .projectKey(project.getKey())
                            .blockers(facetSeverityCount(severities, "BLOCKER"))
                            .criticals(facetSeverityCount(severities, "CRITICAL"))
                            .majors(facetSeverityCount(severities, "MAJOR"))
                            .minors(facetSeverityCount(severities, "MINOR"))
                            .infos(facetSeverityCount(severities, "INFO"))
                            .build()
            );
        });
    }

    private int facetSeverityCount(SonarQubeIssuesFacet severities, String severity) {
        return Integer.parseInt(severities.getByProperty("val", severity, "count"));
    }

}
