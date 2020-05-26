package pl.consdata.ico.sqcompanion.config.service.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.deserialization.GroupDeserializer;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.group.GroupValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupConfigService {

    private final AppConfig appConfig;
    private final GroupValidator validator;
    private final SettingsService settingsService;

    public ValidationResult create(String parentUuid, GroupDefinition groupDefinition) {
        ValidationResult validationResult = validator.validate(groupDefinition).and(validator.groupExists(parentUuid));
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(parentUuid);
            if (parentGroup.getGroups() == null) {
                parentGroup.setGroups(new ArrayList<>());
            }
            GroupDeserializer.ensureParams(groupDefinition);
            parentGroup.getGroups().add(groupDefinition);
            return settingsService.save();
        } else {
            log.info("Invalid group definition {} reason: {}", groupDefinition, validationResult);
            return validationResult;
        }
    }

    public ValidationResult update(GroupDefinition groupDefinition) {
        ValidationResult validationResult = validator.validate(groupDefinition).and(validator.groupExists(groupDefinition.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = this.appConfig.getGroup(groupDefinition.getUuid());
            group.setName(groupDefinition.getName());
            group.setDescription(groupDefinition.getDescription());
            return settingsService.save();
        } else {
            log.info("Invalid group definition {} reason: {}", groupDefinition, validationResult);
            return validationResult;
        }
    }

    public ValidationResult delete(String parentUuid, String uuid) {
        ValidationResult validationResult = validator.groupExists(parentUuid);
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(parentUuid);
            if (parentGroup.getGroups() != null) {
                parentGroup.getGroups().removeIf(group -> group.getUuid().equals(uuid));
            }
        } else {
            log.info("Invalid group uuid {} reason: {}", parentUuid, validationResult);
            return validationResult;
        }
        return settingsService.save();
    }


    public GroupDefinition get(String uuid) {
        return this.appConfig.getGroup(uuid);
    }

    public GroupDefinition getRootGroup() {
        return appConfig.getRootGroup();
    }

    public ValidationResult updateRootGroup(GroupDefinition groupDefinition) {
        ValidationResult validationResult = validator.validateRoot(groupDefinition);
        if (validationResult.isValid()) {
            this.appConfig.getRootGroup().setName(groupDefinition.getName());
            this.appConfig.getRootGroup().setDescription(groupDefinition.getDescription());
            return settingsService.save();
        } else {
            log.info("Invalid group definition {} reason: {}", groupDefinition, validationResult);
            return validationResult;
        }
    }

    public List<GroupDefinition> getSubgroups(String uuid) {
        return get(uuid).getGroups();
    }

    public ValidationResult updateSubgroups(String uuid, List<GroupDefinition> groupDefinitions) {
        ValidationResult validationResult = validator.validate(groupDefinitions);
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            group.setGroups(groupDefinitions);
            return settingsService.save();
        } else {
            log.info("Unable to update subgroups, reason: {}", validationResult);
            return validationResult;
        }
    }

    public Optional<GroupDefinition> getGroupParent(String uuid) {
        return ofNullable(appConfig.getGroupParent(uuid));
    }

    public List<GroupLightModel> getAll() {
        return appConfig.getGroupList(appConfig.getRootGroup().getUuid(), appConfig.getRootGroup().getGroups());
    }
}
