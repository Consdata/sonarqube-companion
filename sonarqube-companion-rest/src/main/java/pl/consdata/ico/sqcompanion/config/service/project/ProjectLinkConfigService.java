package pl.consdata.ico.sqcompanion.config.service.project;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.service.group.GroupConfigService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.group.GroupValidator;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectLinkConfigService {
    private final GroupValidator validator;
    private final GroupConfigService groupConfigService;
    private final SettingsService settingsService;
    private final AppConfig appConfig;

    public List<ProjectLink> getProjectLinks(String uuid) {
        return groupConfigService.get(uuid).getProjectLinks();
    }

    public ValidationResult createProjectLink(String uuid, ProjectLink projectLink) {
        ValidationResult validationResult = validator.validate(projectLink).and(validator.projectLinkNotExists(uuid, projectLink.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            if (group.getProjectLinks() == null) {
                group.setProjectLinks(new ArrayList<>());
            }
            group.getProjectLinks().add(projectLink);
            return settingsService.save();
        } else {
            log.info("Unable to create projectLink definition {} reason: {}", projectLink, validationResult);
            return validationResult;
        }
    }

    public ValidationResult updateProjectLink(String uuid, ProjectLink projectLink) {
        ValidationResult validationResult = validator.validate(projectLink).and(validator.projectLinkExists(uuid, projectLink.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            group.getProjectLinks().stream().filter(link -> link.getUuid().equals(projectLink.getUuid())).findFirst().ifPresent(link -> {
                link.setType(projectLink.getType());
                link.setServerId(projectLink.getServerId());
                link.setConfig(projectLink.getConfig());
            });
            return settingsService.save();
        } else {
            log.info("Unable to update projectLink definition {} reason: {}", projectLink, validationResult);
            return validationResult;
        }
    }

    public ValidationResult deleteProjectLink(String uuid, String projectLinkUuid) {
        ValidationResult validationResult = validator.projectLinkExists(uuid, projectLinkUuid);
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(uuid);
            ofNullable(parentGroup.getProjectLinks()).orElse(new ArrayList<>()).removeIf(projectLink -> projectLink.getUuid().equals(projectLinkUuid));
        } else {
            log.info("Unable to delete project link reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();

    }

}
