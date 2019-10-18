package pl.consdata.ico.sqcompanion.config.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.service.group.GroupConfigService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.group.GroupValidator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupEventConfigService {
    private final GroupValidator validator;
    private final GroupConfigService groupConfigService;
    private final SettingsService settingsService;
    private final AppConfig appConfig;

    public List<GroupEvent> getEvents(String uuid) {
        return ofNullable(groupConfigService.get(uuid).getEvents()).orElse(emptyList());
    }

    public ValidationResult createEvent(String uuid, GroupEvent groupEvent) {
        ValidationResult validationResult = validator.validate(groupEvent).and(validator.groupEventNotExists(uuid, groupEvent.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            if (group.getEvents() == null) {
                group.setEvents(new ArrayList<>());
            }
            group.getEvents().add(groupEvent);
            return settingsService.save();
        } else {
            log.info("Invalid groupEvent definition {} reason: {}", groupEvent, validationResult);
            return validationResult;
        }
    }

    public ValidationResult updateEvent(String uuid, GroupEvent groupEvent) {
        ValidationResult validationResult = validator.validate(groupEvent).and(validator.groupEventExists(uuid, groupEvent.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            group.getEvents().stream().filter(event -> event.getUuid().equals(groupEvent.getUuid())).findFirst().ifPresent(event -> {
                event.setType(groupEvent.getType());
                event.setName(groupEvent.getName());
                event.setDescription(groupEvent.getDescription());
                event.setData(groupEvent.getData());
            });
            return settingsService.save();
        } else {
            log.info("Invalid groupEvent definition {} reason: {}", groupEvent, validationResult);
            return validationResult;
        }
    }

    public ValidationResult deleteEvent(String uuid, String eventUuid) {
        ValidationResult validationResult = validator.groupEventExists(uuid, eventUuid);
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(uuid);
            ofNullable(parentGroup.getEvents()).orElse(new ArrayList<>()).removeIf(event -> event.getUuid().equals(eventUuid));
        } else {
            log.info("Unable to delete event reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }
}
