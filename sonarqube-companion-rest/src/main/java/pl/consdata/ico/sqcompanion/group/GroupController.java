package pl.consdata.ico.sqcompanion.group;

import io.swagger.annotations.ApiOperation;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummaryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final RepositoryService repositoryService;
    private final ProjectSummaryService projectSummaryService;
    private final HealthCheckService healthCheckService;

    public GroupController(
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
            value = "Returns root group details.",
            notes = "<p>Returns root group details with current violations state, health status and sub groups and projects.</p>"
    )
    public GroupDetails getRootGroup() {
        return asGroupDetails(repositoryService.getRootGroup());
    }

    @RequestMapping(
            value = "/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns group details.",
            notes = "<p>Returns group details with current violations state, health status and sub groups and projects.</p>"
    )
    public GroupDetails getGroup(@PathVariable final String uuid) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return asGroupDetails(group.get());
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    private GroupDetails asGroupDetails(final Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);

        return GroupDetails
                .builder()
                .groups(group.getGroups().stream().map(this::asGroupSummary).collect(Collectors.toList()))
                .uuid(group.getUuid())
                .name(group.getName())
                .projects(projectSummaries)
                .healthStatus(healthStatus)
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .build();
    }

    private GroupSummary asGroupSummary(final Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);
        return GroupSummary
                .builder()
                .healthStatus(healthStatus)
                .uuid(group.getUuid())
                .name(group.getName())
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .build();
    }

}
