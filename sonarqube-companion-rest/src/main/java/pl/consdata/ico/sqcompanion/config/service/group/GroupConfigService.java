package pl.consdata.ico.sqcompanion.config.service.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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
            groupDefinition.setGroups(ofNullable(groupDefinition.getGroups()).orElse(new ArrayList<>()));
            groupDefinition.setEvents(ofNullable(groupDefinition.getEvents()).orElse(new ArrayList<>()));
            groupDefinition.setProjectLinks(ofNullable(groupDefinition.getProjectLinks()).orElse(new ArrayList<>()));
            groupDefinition.setWebhooks(ofNullable(groupDefinition.getWebhooks()).orElse(new ArrayList<>()));
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

    public List<ProjectLink> getProjectLinks(String uuid) {
        return get(uuid).getProjectLinks();
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
            ofNullable(parentGroup.getProjectLinks()).orElse(emptyList()).removeIf(projectLink -> projectLink.getUuid().equals(projectLinkUuid));
        } else {
            log.info("Unable to delete project link reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();

    }

    public List<GroupEvent> getEvents(String uuid) {
        return get(uuid).getEvents();
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
            ofNullable(parentGroup.getEvents()).orElse(emptyList()).removeIf(event -> event.getUuid().equals(eventUuid));
        } else {
            log.info("Unable to delete event reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }

    public List<WebhookDefinition> getWebhooks(String uuid) {
        return get(uuid).getWebhooks();
    }

    public ValidationResult createWebhook(String uuid, WebhookDefinition webhookDefinition) {
        ValidationResult validationResult = validator.validate(webhookDefinition).and(validator.webhookNotExists(uuid, webhookDefinition.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            if (group.getWebhooks() == null) {
                group.setWebhooks(new ArrayList<>());
            }
            group.getWebhooks().add(webhookDefinition);
            return settingsService.save();
        } else {
            log.info("Invalid webhook definition {} reason: {}", webhookDefinition, validationResult);
            return validationResult;
        }
    }

    public ValidationResult updateWebhook(String uuid, WebhookDefinition webhookDefinition) {
        ValidationResult validationResult = validator.validate(webhookDefinition).and(validator.webhookExists(uuid, webhookDefinition.getUuid()));
        if (validationResult.isValid()) {
            GroupDefinition group = appConfig.getGroup(uuid);
            group.getWebhooks().stream().filter(webhook -> webhook.getUuid().equals(webhookDefinition.getUuid())).findFirst().ifPresent(webhook -> {
                webhook.setAction(webhookDefinition.getAction());
                webhook.setName(webhookDefinition.getName());
                webhook.setCallbacks(webhookDefinition.getCallbacks());
                webhook.setTrigger(webhookDefinition.getTrigger());
            });
            return settingsService.save();
        } else {
            log.info("Invalid webhook definition {} reason: {}", webhookDefinition, validationResult);
            return validationResult;
        }
    }

    public ValidationResult deleteWebhook(String uuid, String webhookUuid) {
        ValidationResult validationResult = validator.webhookExists(uuid, webhookUuid);
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(uuid);
            ofNullable(parentGroup.getWebhooks()).orElse(emptyList()).removeIf(webhook -> webhook.getUuid().equals(webhookUuid));
        } else {
            log.info("Unable to delete webhook reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
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

    public List<WebhookCallback> getCallbacks(String groupUuid, String webhookUuid) {
        return get(groupUuid).getWebhooks().stream().filter(webhookDefinition -> webhookDefinition.getUuid().equals(webhookUuid)).findFirst().orElse(WebhookDefinition.builder().callbacks(emptyList()).build()).getCallbacks();
    }

    public ValidationResult updateCallback(String groupUuid, String webhookUuid, WebhookCallback callback) {
        GroupDefinition group = appConfig.getGroup(groupUuid);
        Optional<WebhookDefinition> webhookDefinitionOptional = group.getWebhooks().stream().filter(webhookDefinition -> webhookDefinition.getUuid().equals(webhookUuid)).findFirst();

        if (webhookDefinitionOptional.isPresent()) {
            WebhookDefinition webhookDefinition = webhookDefinitionOptional.get();
            ValidationResult validationResult = validator.validate(callback).and(validator.webhookCallbackNotExists(groupUuid, webhookDefinition.getUuid(), callback.getUuid()));
            if (validationResult.isValid()) {
                webhookDefinition.setCallbacks(
                        webhookDefinition.getCallbacks()
                                .stream()
                                .map(
                                        c -> {

                                            if (c.getUuid().equals(callback.getUuid())) {
                                                //TODO refactor
                                                if (callback instanceof JSONWebhookCallback) {
                                                    return new JSONWebhookCallback((JSONWebhookCallback) callback, c.getUuid());
                                                }

                                                if (callback instanceof PostWebhookCallback) {
                                                    return new PostWebhookCallback((PostWebhookCallback) callback, c.getUuid());
                                                }
                                                return c;
                                            } else {
                                                return c;
                                            }
                                        }
                                ).collect(Collectors.toList()));
                return settingsService.save();
            } else {
                log.info("Invalid callback definition {} reason: {}", callback, validationResult);
                return validationResult;
            }
        } else {
            log.info("Invalid webhook definition {} reason: {}", webhookDefinitionOptional, "Parent webhook does not exist");
            return ValidationResult.invalid("WEBHOOK_NOT_EXISTS", "Parent webhook does not exist");
        }
    }

    public ValidationResult createCallback(String groupUuid, String webhookUuid, WebhookCallback callback) {
        GroupDefinition group = appConfig.getGroup(groupUuid);
        Optional<WebhookDefinition> webhookDefinitionOptional = group.getWebhooks().stream().filter(webhookDefinition -> webhookDefinition.getUuid().equals(webhookUuid)).findFirst();

        if (webhookDefinitionOptional.isPresent()) {
            WebhookDefinition webhookDefinition = webhookDefinitionOptional.get();
            ValidationResult validationResult = validator.validate(callback).and(validator.webhookCallbackExists(groupUuid, webhookDefinition.getUuid(), callback.getUuid()));
            if (validationResult.isValid()) {
                if (webhookDefinition.getCallbacks() == null) {
                    webhookDefinition.setCallbacks(new ArrayList<>());
                }
                webhookDefinition.getCallbacks().add(callback);
                return settingsService.save();
            } else {
                log.info("Invalid callback definition {} reason: {}", callback, validationResult);
                return validationResult;
            }
        } else {
            log.info("Invalid webhook definition {} reason: {}", webhookDefinitionOptional, "Parent webhook does not exist");
            return ValidationResult.invalid("WEBHOOK_NOT_EXISTS", "Parent webhook does not exist");
        }
    }

    public ValidationResult deleteCallback(String groupUuid, String webhookUuid, String callbackUuid) {
        ValidationResult validationResult = validator.webhookCallbackExists(groupUuid, webhookUuid, callbackUuid);
        if (validationResult.isValid()) {
            GroupDefinition parentGroup = this.appConfig.getGroup(groupUuid);
            ofNullable(parentGroup.getWebhooks()).orElse(emptyList())
                    .stream()
                    .filter(webhook -> webhook.getUuid().equals(webhookUuid))
                    .findFirst()
                    .ifPresent(webhookDefinition -> ofNullable(webhookDefinition.getCallbacks())
                            .orElse(emptyList())
                            .removeIf(callback -> callback.getUuid().equals(callbackUuid)));
        } else {
            log.info("Unable to delete callback, reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }
}
