package pl.consdata.ico.sqcompanion.overview;

import io.swagger.annotations.ApiOperation;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummaryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/overview")
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

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns system overview.",
            notes = "<p>Returns groups tree with names, hierarchy and health status.</p>"
    )
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
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .projectCount(projectSummaries.size())
                .groups(group.getGroups().stream().map(this::asGroupWithSubGroupsSummary).collect(Collectors.toList()))
                .build();
    }

}
