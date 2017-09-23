package pl.consdata.ico.sqcompanion.project;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectSummaryService projectSummaryService;
    private final RepositoryService repositoryService;
    public ProjectController(ProjectSummaryService projectSummaryService, RepositoryService repositoryService) {
        this.projectSummaryService = projectSummaryService;
        this.repositoryService = repositoryService;
    }

    @RequestMapping(
            value = "/{uuid}/{projectKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project summary.",
            notes = "<p>Returns project summary with current violations state and health status.</p>"
    )
    public ProjectSummary getGroup(@PathVariable final String uuid, @PathVariable final String projectKey) {
        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent()) {
            return projectSummaryService.getProjectSummary(project.get());
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }
}
