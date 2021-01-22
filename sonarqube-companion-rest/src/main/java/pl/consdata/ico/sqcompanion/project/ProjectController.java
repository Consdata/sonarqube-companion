package pl.consdata.ico.sqcompanion.project;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryService;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistoryService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectSummaryService projectSummaryService;
    private final RepositoryService repositoryService;
    private final UserViolationSummaryHistoryService userViolationSummaryHistoryService;
    private final ProjectViolationsHistoryService projectViolationsHistoryService;

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
            return userViolationSummaryHistoryService.getProjectSummary(project.get(), uuid);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }





    // TODO przenieś do proejctDiffController a tamte do czegoś dla grupy i lepsze endpoin niz project/diff
    @RequestMapping(
            value = "diff/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public GroupViolationsHistoryDiff getProjectViolationsHistory(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        return projectViolationsHistoryService.getGroupViolationsHistoryDiff(repositoryService.getRootGroup(), fromDate, toDate);
    }


    // TODO przenieś do proejctDiffController a tamte do czegoś dla grupy i lepsze endpoin niz project/diff
    @RequestMapping(
            value = "diff2/{projectKey:.+}/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public GroupViolationsHistoryDiff getProjectViolationsHistory(
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        return projectViolationsHistoryService.getGroupViolationsHistoryDiff(repositoryService.getRootGroup(), projectKey, fromDate, toDate);
    }

    @RequestMapping(
            value = "/{projectKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project summary.",
            notes = "<p>Returns project summary with current violations state and health status.</p>"
    )
    public ProjectSummary getProject(@PathVariable final String projectKey) {
        final Optional<Project> project = repositoryService.getProject(projectKey);
        if (project.isPresent()) {
            return userViolationSummaryHistoryService.getProjectSummary(project.get());
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey);
        }
    }

    @RequestMapping(
            value = "/diff/{projectKey:.+}/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history change in time"
    )
    public ProjectViolationsHistoryDiff geProjectViolationsHistoryDiff(
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        final Optional<Project> project = repositoryService.getProject(projectKey);
        if (project.isPresent()) {
            return projectViolationsHistoryService.getProjectViolationsHistoryDiff(project.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey);
        }
    }


    @RequestMapping(
            value = "/diff/{projectKey:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public ViolationsHistory getProjectViolationsHistory(
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
