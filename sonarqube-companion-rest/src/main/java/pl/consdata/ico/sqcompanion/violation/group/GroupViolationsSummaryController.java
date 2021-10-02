package pl.consdata.ico.sqcompanion.violation.group;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.members.MembersViolationsSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/violations/summary/group")
@RequiredArgsConstructor
public class GroupViolationsSummaryController {

    private final RepositoryService repositoryService;
    private final GroupMembersViolationsSummaryService groupMembersViolationsSummaryService;
    private final GroupProjectsViolationsSummaryService groupProjectsViolationsSummaryService;


    @GetMapping(
            value = "{uuid}/members/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns groups members violations summary.",
            notes = "<p>Returns members violations summary in given range.</p>"
    )
    public List<MembersViolationsSummary> members(@PathVariable String uuid,
                                                  @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
                                                  @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return groupMembersViolationsSummaryService.getGroupMembersViolationsSummary(group.get(), from, to);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }


    @GetMapping(
            value = "{uuid}/projects/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns groups projects summary.",
            notes = "<p>Returns projects violations summary in given range.</p>"
    )
    public List<ProjectViolationsSummary> projects(@PathVariable String uuid,
                                                   @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
                                                   @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return groupProjectsViolationsSummaryService.getGroupProjectsViolationsHistoryDiff(group.get(), from, to);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }
}
