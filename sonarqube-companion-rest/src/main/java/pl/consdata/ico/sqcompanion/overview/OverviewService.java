package pl.consdata.ico.sqcompanion.overview;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummaryService;
import pl.consdata.ico.sqcompanion.repository.Group;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OverviewService {

    private final ProjectSummaryService projectSummaryService;
    private final HealthCheckService healthCheckService;

    public OverviewService(final ProjectSummaryService projectSummaryService,
                           final HealthCheckService healthCheckService) {
        this.projectSummaryService = projectSummaryService;
        this.healthCheckService = healthCheckService;
    }

    @Cacheable(value = Caches.GROUP_OVERVIEW_CACHE, sync = true, key = "#group.uuid")
    public GroupOverview getOverview(Group group) {
        return asGroupWithSubGroupsSummary(group);
    }

    private GroupOverview asGroupWithSubGroupsSummary(final Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);
        return GroupOverview
                .builder()
                .healthStatus(healthStatus)
                .uuid(group.getUuid())
                .name(group.getName())
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .projectCount(projectSummaries.size())
                .groups(group.getGroups().stream().map(this::asGroupWithSubGroupsSummary).collect(Collectors.toList()))
                .build();
    }

}
