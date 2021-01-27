package pl.consdata.ico.sqcompanion.violation.group;

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
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistoryService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/violations/history/group")
@RequiredArgsConstructor
public class GroupViolationsHistoryController {

    private final RepositoryService repositoryService;
    private final UserViolationSummaryHistoryService userViolationsHistoryService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group violations history"
    )
    public ViolationsHistory getRootGroupViolationsHistory(@RequestParam Optional<Integer> daysLimit) {
        return userViolationsHistoryService.getGroupViolationsHistory(repositoryService.getRootGroup(), daysLimit);
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group violations history"
    )
    public ViolationsHistory getGroupViolationsHistory(@PathVariable final String uuid, @RequestParam Optional<Integer> daysLimit) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return userViolationsHistoryService.getGroupViolationsHistory(group.get(), daysLimit);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/{fromDate}/{toDate}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group violations history change in time"
    )
    public GroupViolationsHistoryDiff getGroupViolationsHistoryDiff(
            @PathVariable final String uuid,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return userViolationsHistoryService.getGroupsUserViolationsHistoryDiff(group.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/project/{uuid}/{projectKey:.+}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history"
    )
    public ViolationsHistory getProjectViolationsHistory(
            @PathVariable final String uuid,
            @PathVariable final String projectKey,
            @RequestParam Optional<Integer> daysLimit) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent() && group.isPresent()) {
            return userViolationsHistoryService.getProjectViolationsHistory(group.get(), project.get(), daysLimit);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/project/{projectKey:.+}/{fromDate}/{toDate}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history change in time"
    )
    public ProjectViolationsHistoryDiff geProjectViolationsHistoryDiff(
            @PathVariable final String uuid,
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent() && group.isPresent()) {
            return userViolationsHistoryService.getProjectViolationsHistoryDiff(group.get(), project.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }


}
