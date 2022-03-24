package pl.consdata.ico.sqcompanion.config.service.group;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.StringUtils.EMPTY;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/group")
public class GroupConfigController extends SettingsExceptionHandler {

    private final GroupConfigService groupConfigService;

    @ApiOperation(value = "Create new group definition",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/{parentUuid}/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult create(@PathVariable String parentUuid) {
        log.info("Create new child for {}", parentUuid);
        return groupConfigService.create(parentUuid);
    }

    @ApiOperation(value = "Update group definition",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult update(@RequestBody GroupDefinition groupDefinition) {
        log.info("Update group definition {}", groupDefinition);
        return groupConfigService.update(groupDefinition);
    }

    @ApiOperation(value = "Delete group definition",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "/{parentUuid}/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult delete(@PathVariable String parentUuid, @PathVariable String uuid) {
        log.info("Delete group definition {}/{}", parentUuid, uuid);
        return groupConfigService.delete(parentUuid, uuid);
    }

    @ApiOperation(value = "Get group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDefinition get(@PathVariable String uuid) {
        log.info("Get group definition {}", uuid);
        return groupConfigService.get(uuid);
    }

    @ApiOperation(value = "Get root group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDefinition getRootGroup() {
        log.info("Get root group definition");
        return groupConfigService.getRootGroup();
    }

    @ApiOperation(value = "Update root group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult updateRootGroup(@RequestBody GroupDefinition groupDefinition) {
        log.info("Update root group definition");
        return groupConfigService.updateRootGroup(groupDefinition);
    }

    @ApiOperation(value = "Get subgroups",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{uuid}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GroupDefinition> getSubgroups(@PathVariable String uuid) {
        log.info("Get subgroups for {}", uuid);
        return groupConfigService.getSubgroups(uuid);
    }

    @ApiOperation(value = "Update subgroups",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/{uuid}/groups", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult getSubgroups(@PathVariable String uuid, @RequestBody List<GroupDefinition> groupDefinitions) {
        log.info("Update subgroups for {}", uuid);
        return groupConfigService.updateSubgroups(uuid, groupDefinitions);
    }


    @GetMapping(
            value = "/{uuid}/parent",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ApiOperation(
            value = "Returns group parent.",
            notes = "<p>Returns group parent</p>"
    )
    public ResponseEntity<String> getGroupParent(@PathVariable final String uuid) {
        return groupConfigService.getGroupParent(uuid)
                .map(groupDefinition -> ResponseEntity.ok(groupDefinition.getUuid()))
                .orElseGet(() -> ResponseEntity.ok(EMPTY));
    }


    @GetMapping(
            value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns light groups list",
            notes = "<p>Returns light groups list</p>"
    )
    public List<GroupLightModel> getAll() {
        return groupConfigService.getAll();
    }

    @GetMapping(
            value = "/crumbs/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Returns group parents",
            notes = "<p>Returns group parents</p>"
    )
    public List<GroupLightModel> getCrumbs(@PathVariable Optional<String> uuid) {
        return groupConfigService.crumbs(uuid);
    }


}
