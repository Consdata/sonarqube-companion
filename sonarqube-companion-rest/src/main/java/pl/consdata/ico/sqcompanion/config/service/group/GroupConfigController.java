package pl.consdata.ico.sqcompanion.config.service.group;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/group")
public class GroupConfigController extends SettingsExceptionHandler {

    private final GroupConfigService groupConfigService;


    @ApiOperation(value = "Create new group definition",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{parentUuid}/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult create(@PathVariable String parentUuid, @RequestBody GroupDefinition groupDefinition) {
        log.info("Create new group {}/{}", parentUuid, groupDefinition);
        return groupConfigService.create(parentUuid, groupDefinition);
    }

    @ApiOperation(value = "Update group definition",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult update(@RequestBody GroupDefinition groupDefinition) {
        log.info("Update group definition {}", groupDefinition);
        return groupConfigService.update(groupDefinition);
    }

    @ApiOperation(value = "Delete group definition",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{parentUuid}/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult delete(@PathVariable String parentUuid, @PathVariable String uuid) {
        log.info("Delete group definition {}/{}", parentUuid, uuid);
        return groupConfigService.delete(parentUuid, uuid);
    }

    @ApiOperation(value = "Get group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GroupDefinition get(@PathVariable String uuid) {
        log.info("Get group definition {}", uuid);
        return groupConfigService.get(uuid);
    }

    @ApiOperation(value = "Get root group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GroupDefinition getRootGroup() {
        log.info("Get root group definition");
        return groupConfigService.getRootGroup();
    }

    @ApiOperation(value = "Update root group definition",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateRootGroup(@RequestBody GroupDefinition groupDefinition) {
        log.info("Update root group definition");
        return groupConfigService.updateRootGroup(groupDefinition);
    }

    @ApiOperation(value = "Get group project links",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{groupUuid}/projectLinks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ProjectLink> getProjectLinks(@PathVariable String groupUuid) {
        log.info("Get group project links {}", groupUuid);
        return groupConfigService.getProjectLinks(groupUuid);
    }

    @ApiOperation(value = "Create project link",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/projectLinks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createProjectLink(@PathVariable String groupUuid, @RequestBody ProjectLink projectLink) {
        log.info("Create project link [{}] {}", groupUuid, projectLink);
        return groupConfigService.createProjectLink(groupUuid, projectLink);
    }

    @ApiOperation(value = "Update project link",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/projectLinks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateProjectLink(@PathVariable String groupUuid, @RequestBody ProjectLink projectLink) {
        log.info("Update project link [{}] {}", groupUuid, projectLink);
        return groupConfigService.updateProjectLink(groupUuid, projectLink);
    }

    @ApiOperation(value = "Delete project link",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{groupUuid}/projectLinks/{projectLinkUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult deleteProjectLink(@PathVariable String groupUuid, @PathVariable String projectLinkUuid) {
        log.info("Delete project link {}/{}", groupUuid, projectLinkUuid);
        return groupConfigService.deleteProjectLink(groupUuid, projectLinkUuid);
    }

    @ApiOperation(value = "Get group events",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}/events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupEvent> getEvents(@PathVariable String uuid) {
        log.info("Get group events {}", uuid);
        return groupConfigService.getEvents(uuid);
    }

    @ApiOperation(value = "Create group event",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/events/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createEvent(@PathVariable String uuid, @RequestBody GroupEvent groupEvent) {
        log.info("Create group event [{}] {}", uuid, groupEvent);
        return groupConfigService.createEvent(uuid, groupEvent);
    }

    @ApiOperation(value = "Update group event",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/events/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateEvent(@PathVariable String uuid, @RequestBody GroupEvent groupEvent) {
        log.info("Update group event [{}] {}", uuid, groupEvent);
        return groupConfigService.updateEvent(uuid, groupEvent);

    }

    @ApiOperation(value = "Delete group event",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{uuid}/events/{eventUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult deleteEvent(@PathVariable String uuid, @PathVariable String eventUuid) {
        log.info("Delete group event {}/{}", uuid, eventUuid);
        return groupConfigService.deleteEvent(uuid, eventUuid);
    }

    @ApiOperation(value = "Get group webhooks",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}/webhooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WebhookDefinition> getWebhooks(@PathVariable String uuid) {
        log.info("Get group webhooks {}", uuid);
        return groupConfigService.getWebhooks(uuid);
    }

    @ApiOperation(value = "Create group webhook",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/webhooks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createWebhook(@PathVariable String uuid, @RequestBody WebhookDefinition webhookDefinition) {
        log.info("Create group webhook [{}] {}", uuid, webhookDefinition);
        return groupConfigService.createWebhook(uuid, webhookDefinition);
    }

    @ApiOperation(value = "Update group webhook",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/webhooks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateWebhook(@PathVariable String uuid, @RequestBody WebhookDefinition webhookDefinition) {
        log.info("Update group webhook [{}] {}", uuid, webhookDefinition);
        return groupConfigService.updateWebhook(uuid, webhookDefinition);
    }

    @ApiOperation(value = "Delete group webhook",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{uuid}/webhooks/{webhookUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult deleteWebhook(@PathVariable String uuid, @PathVariable String webhookUuid) {
        log.info("Delete group webhook [{}/{}]", uuid, webhookUuid);
        return groupConfigService.deleteWebhook(uuid, webhookUuid);
    }

    @ApiOperation(value = "Get subgroups",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{uuid}/groups", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupDefinition> getSubgroups(@PathVariable String uuid) {
        log.info("Get subgroups for {}", uuid);
        return groupConfigService.getSubgroups(uuid);
    }

    @ApiOperation(value = "Update subgroups",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{uuid}/groups", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult getSubgroups(@PathVariable String uuid, @RequestBody List<GroupDefinition> groupDefinitions) {
        log.info("Update subgroups for {}", uuid);
        return groupConfigService.updateSubgroups(uuid, groupDefinitions);
    }

    @ApiOperation(value = "Get webook callbacks",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<WebhookCallback> getCallbacks(@PathVariable String groupUuid, @PathVariable String webhookUuid) {
        log.info("Get callbacks for {}/{}", groupUuid, webhookUuid);
        return groupConfigService.getCallbacks(groupUuid, webhookUuid);
    }

    @ApiOperation(value = "Create callback",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createCallback(@PathVariable String groupUuid, @PathVariable String webhookUuid, @RequestBody WebhookCallback callback) {
        log.info("Create callback for {}/{}", groupUuid, webhookUuid);
        return groupConfigService.createCallback(groupUuid, webhookUuid, callback);
    }

    @ApiOperation(value = "Update callback",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateCallback(@PathVariable String groupUuid, @PathVariable String webhookUuid, @RequestBody WebhookCallback callback) {
        log.info("Update callback for {}/{}", groupUuid, webhookUuid);
        return groupConfigService.updateCallback(groupUuid, webhookUuid, callback);
    }

    @ApiOperation(value = "Delete callback",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{groupUuid}/webhooks/{webhookUuid}/callbacks/{callbackUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult getCallbacks(@PathVariable String groupUuid, @PathVariable String webhookUuid, @PathVariable String callbackUuid) {
        log.info("Delete callback {}/{}/{}", groupUuid, webhookUuid, callbackUuid);
        return groupConfigService.deleteCallback(groupUuid, webhookUuid, callbackUuid);
    }
}
