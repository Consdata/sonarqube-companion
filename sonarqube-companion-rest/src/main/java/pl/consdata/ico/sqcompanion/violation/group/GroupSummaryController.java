package pl.consdata.ico.sqcompanion.violation.group;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistoryService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/violations/group")
@RequiredArgsConstructor
public class GroupSummaryController {

    private final RepositoryService repositoryService;
    private final UserViolationSummaryHistoryService userViolationsHistoryService;


    @RequestMapping(
            value = "{uuid}/summary/project/{projectKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project summary.",
            notes = "<p>Returns project summary with current violations state and health status.</p>"
    )
    public ProjectSummary getProject(@PathVariable final String projectKey, @PathVariable String uuid) {
        final Optional<Project> project = repositoryService.getProject(projectKey);
        if (project.isPresent()) {
            return userViolationsHistoryService.getProjectSummary(project.get(), uuid);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " for uuid: " + uuid);
        }
    }
}
