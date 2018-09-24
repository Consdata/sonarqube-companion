package pl.consdata.ico.sqcompanion.group;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.util.Optional;

/**
 * @author gregorry
 */
@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final RepositoryService repositoryService;
    private final GroupService groupService;

    public GroupController(
            final RepositoryService repositoryService,
            final GroupService groupService) {
        this.repositoryService = repositoryService;
        this.groupService = groupService;
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
        return groupService.getGroupDetails(repositoryService.getRootGroup());
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
            return groupService.getGroupDetails(group.get());
        } else {
            throw new SQCompanionException("Can't find requested group uuid: " + uuid);
        }
    }

}
