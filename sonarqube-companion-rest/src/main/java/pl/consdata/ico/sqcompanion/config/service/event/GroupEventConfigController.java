package pl.consdata.ico.sqcompanion.config.service.event;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.service.group.GroupConfigService;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/group")
public class GroupEventConfigController extends SettingsExceptionHandler {

    private final GroupConfigService groupConfigService;


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
}