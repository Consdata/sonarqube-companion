package pl.consdata.ico.sqcompanion.group;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final RepositoryService repositoryService;
    private final GroupService groupService;
    private final MemberService memberService;

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns root group details.",
            notes = "<p>Returns root group details with current violations state, health status and sub groups and projects.</p>"
    )
    public GroupDetails getRootGroup() {
        return groupService.getRootGroupDetails();
    }

    @GetMapping(
            value = "/{uuid}/info",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group basic data",
            notes = "<p>Returns group basic data</p>"
    )
    public GroupLightModel info(@PathVariable final String uuid) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return GroupLightModel.of(group.get());
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }


    @RequestMapping(
            value = "/{uuid}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group details.",
            notes = "<p>Returns group details with current violations state, health status and sub groups and projects.</p>"
    )
    public GroupDetails getGroup(@PathVariable final String uuid) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return groupService.getGroupDetails(group.get());
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/members",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group members.",
            notes = "<p>Returns group members</p>"
    )
    public Set<Member> getMembers(@PathVariable final String uuid) {
        return memberService.groupMembers(uuid);
    }


    @ApiOperation(value = "Get all members aliases",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{uuid}/aliases", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getMemberGroups(@PathVariable String uuid) {
        return memberService.groupMembers(uuid).stream().map(Member::getAliases).flatMap(Set::stream).collect(Collectors.toList());
    }

    @GetMapping(
            value = "/{uuid}/members/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group members between given period.",
            notes = "<p>Returns group members</p>"
    )
    public Set<Member> getMembers(@PathVariable final String uuid, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return memberService.groupMembers(uuid, from, to);
    }


    @GetMapping(
            value = "/{uuid}/violations/{date}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Violations for given date.",
            notes = "<p>Violations for given date</p>"
    )
    public Violations getViolations(@PathVariable final String uuid, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return groupService.getViolations(group.get(), date);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

    @GetMapping(
            value = "/{uuid}/violations/{from}/{to}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Violations diff for given range",
            notes = "<p>Violations diff for given range</p>"
    )
    public Violations getViolationsRange(@PathVariable final String uuid
            , @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from
            , @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to) {
        final Optional<Group> group = repositoryService.getGroup(uuid);
        if (group.isPresent()) {
            return groupService.getViolationsDiff(group.get(), from, to);
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }


}
