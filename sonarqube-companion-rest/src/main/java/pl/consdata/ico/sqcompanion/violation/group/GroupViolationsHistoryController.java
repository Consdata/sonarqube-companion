package pl.consdata.ico.sqcompanion.violation.group;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.members.MembersViolationsSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationsHistoryService;
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/violations/history/group")
@RequiredArgsConstructor
public class GroupViolationsHistoryController {

    private final RepositoryService repositoryService;
    private final GroupViolationsHistoryService groupViolationsHistoryService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group violations history"
    )
    public ViolationsHistory getRootGroupViolationsHistory(@RequestParam Optional<Integer> daysLimit) {
        return groupViolationsHistoryService.getGroupViolationsHistory(repositoryService.getRootGroup(), daysLimit);
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
            return this.groupViolationsHistoryService.getGroupViolationsHistory(group.get(), daysLimit);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/{fromDate}/{toDate}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group violations history"
    )
    public ViolationsHistory getGroupViolationsHistory(@PathVariable final String uuid,
                                                       @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") final LocalDate fromDate,
                                                       @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") final LocalDate toDate) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return this.groupViolationsHistoryService.getGroupViolationsHistory(group.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/{fromDate}/{toDate}/diff",
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
            return this.groupViolationsHistoryService.getGroupViolationsHistoryDiff(group.get(), fromDate, toDate);
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
            return this.groupViolationsHistoryService.getProjectViolationsHistory(group.get(), project.get(), daysLimit);
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
    public ProjectViolationsSummary geProjectViolationsHistoryDiff(
            @PathVariable final String uuid,
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent() && group.isPresent()) {
            return this.groupViolationsHistoryService.getProjectViolationsHistoryDiff(group.get(), project.get(), fromDate, toDate);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }


    @GetMapping(
            value = "/{uuid}/projects/{fromDate}/{toDate}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history change in time" //TODO
    )
    public List<ProjectViolationsSummary> get(
            @PathVariable final String uuid,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate,
            @RequestParam Optional<List<String>> members) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return this.groupViolationsHistoryService.getGroupProjectsViolationsHistoryDiff(group.get(), fromDate, toDate, members);
        } else {
            throw new SQCompanionException("Can't find group: " + uuid);
        }
    }


    @GetMapping(
            value = "/{uuid}/projects",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history change in time" //TODO
    )
    public List<ProjectViolationsSummary> get(
            @PathVariable final String uuid,
            @RequestParam Optional<Integer> daysLimit,
            @RequestParam Optional<List<String>> members) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return this.groupViolationsHistoryService.getGroupProjectsViolationsHistoryDiff(group.get(), members, daysLimit);
        } else {
            throw new SQCompanionException("Can't find group: " + uuid);
        }
    }


    @GetMapping(
            value = "/{uuid}/members",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns project violations history change in time" //TODO
    )
    public List<MembersViolationsSummary> getByMemebers(
            @PathVariable final String uuid,
            @RequestParam Optional<Integer> daysLimit,
            @RequestParam Optional<List<String>> projects) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return this.groupViolationsHistoryService.getGroupMembersViolationsHistoryDiff(group.get(), projects, daysLimit);
        } else {
            throw new SQCompanionException("Can't find group: " + uuid);
        }
    }

}
