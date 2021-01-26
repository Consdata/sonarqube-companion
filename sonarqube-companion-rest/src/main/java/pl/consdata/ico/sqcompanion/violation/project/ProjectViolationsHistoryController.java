package pl.consdata.ico.sqcompanion.violation.project;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/violations/history/project")
@RequiredArgsConstructor
public class ProjectViolationsHistoryController {

    private final ProjectViolationsHistoryService projectViolationsHistoryService;
    private final RepositoryService repositoryService;


    @RequestMapping(
            value = "/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public GroupViolationsHistoryDiff getAllProjectsViolationsHistory(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        Group group = repositoryService.getRootGroup();
        return projectViolationsHistoryService.getGroupViolationsHistoryDiff(group, fromDate, toDate);
    }

    @RequestMapping(
            value = "/{projectKey:.+}/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public ProjectViolationsHistoryDiff getAllProjectsViolationsHistory(
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        Optional<Project> project = repositoryService.getProject(projectKey);
        if (project.isPresent()) {
            return projectViolationsHistoryService.getProjectViolationsHistoryDiff(project.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey);
        }

    }

    @RequestMapping(
            value = "/{projectKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public ViolationsHistory getAllProjectsViolationsHistory(
            @PathVariable final String projectKey,
            @RequestParam Optional<Integer> daysLimit) {
        final Optional<Project> project = repositoryService.getProject(projectKey);
        if (project.isPresent()) {
            return projectViolationsHistoryService.getProjectViolationsHistory(project.get(), daysLimit);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey);
        }
    }


}
