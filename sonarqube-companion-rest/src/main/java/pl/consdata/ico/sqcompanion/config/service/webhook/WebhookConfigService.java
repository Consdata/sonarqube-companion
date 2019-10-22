package pl.consdata.ico.sqcompanion.config.service.webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.service.group.GroupConfigService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.group.GroupValidator;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Slf4j
@Service
public class WebhookConfigService {
    private final GroupValidator validator;
    private final GroupConfigService groupConfigService;
    private final SettingsService settingsService;
    private final AppConfig appConfig;


    public List<WebhookDefinition> getWebhooks(String uuid) {
        return groupConfigService.get(uuid).getWebhooks();
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
            ofNullable(parentGroup.getWebhooks()).orElse(new ArrayList<>()).removeIf(webhook -> webhook.getUuid().equals(webhookUuid));
        } else {
            log.info("Unable to delete webhook reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }


    public List<WebhookCallback> getCallbacks(String groupUuid, String webhookUuid) {
        return groupConfigService.get(groupUuid).getWebhooks().stream().filter(webhookDefinition -> webhookDefinition.getUuid().equals(webhookUuid)).findFirst().orElse(WebhookDefinition.builder().callbacks(emptyList()).build()).getCallbacks();
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
                                .map(c -> updateCallback(c, callback)).collect(Collectors.toList()));
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
                            .orElse(new ArrayList<>())
                            .removeIf(callback -> callback.getUuid().equals(callbackUuid)));
        } else {
            log.info("Unable to delete callback, reason: {}", validationResult);
            return validationResult;
        }
        return settingsService.save();
    }

    private WebhookCallback createSpecificCallback(WebhookCallback callback) {
        if (callback instanceof JSONWebhookCallback) {
            return new JSONWebhookCallback((JSONWebhookCallback) callback, callback.getUuid());
        }

        if (callback instanceof PostWebhookCallback) {
            return new PostWebhookCallback((PostWebhookCallback) callback, callback.getUuid());
        }
        return callback;
    }

    private WebhookCallback updateCallback(WebhookCallback dst, WebhookCallback src) {
        if (dst.getUuid().equals(src.getUuid())) {
            return createSpecificCallback(src);
        } else {
            return dst;
        }
    }

}
