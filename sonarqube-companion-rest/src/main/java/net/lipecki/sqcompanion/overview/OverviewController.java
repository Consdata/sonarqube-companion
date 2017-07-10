package net.lipecki.sqcompanion.overview;

import net.lipecki.sqcompanion.health.HealthCheckService;
import net.lipecki.sqcompanion.health.HealthStatus;
import net.lipecki.sqcompanion.project.ProjectSummary;
import net.lipecki.sqcompanion.project.ProjectSummaryService;
import net.lipecki.sqcompanion.repository.Group;
import net.lipecki.sqcompanion.repository.RepositoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/overview")
public class OverviewController {

    private final RepositoryService repositoryService;
    private final ProjectSummaryService projectSummaryService;
    private final HealthCheckService healthCheckService;

    public OverviewController(
            final RepositoryService repositoryService,
            final ProjectSummaryService projectSummaryService,
            final HealthCheckService healthCheckService) {
        this.repositoryService = repositoryService;
        this.projectSummaryService = projectSummaryService;
        this.healthCheckService = healthCheckService;
    }

    @RequestMapping({"", "/"})
    public GroupOverview getOverview() {
        return asGroupWithSubGroupsSummary(repositoryService.getRootGroup());
    }

    private GroupOverview asGroupWithSubGroupsSummary(final Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);
        return GroupOverview
                .builder()
                .healthStatus(healthStatus)
                .uuid(group.getUuid())
                .name(group.getName())
                .groups(group.getGroups().stream().map(this::asGroupWithSubGroupsSummary).collect(Collectors.toList()))
                .build();
    }

}
