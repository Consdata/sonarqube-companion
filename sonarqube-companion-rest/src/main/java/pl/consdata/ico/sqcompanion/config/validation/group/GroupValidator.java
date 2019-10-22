package pl.consdata.ico.sqcompanion.config.validation.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.config.validation.event.GroupEventValidator;
import pl.consdata.ico.sqcompanion.config.validation.project.ProjectLinkConfigValidator;
import pl.consdata.ico.sqcompanion.config.validation.webhook.WebhookConfigValidator;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupValidator {

    private final AppConfig appConfig;
    private final GroupEventValidator groupEventValidator;
    private final WebhookConfigValidator webhookConfigValidator;
    private final ProjectLinkConfigValidator projectLinkConfigValidator;

    public ValidationResult validateRoot(final GroupDefinition groupDefinition) {
        return ValidationResult.valid();
    }

    public ValidationResult validate(final GroupDefinition groupDefinition) {
        return ValidationResult.valid();
    }

    public ValidationResult validate(final WebhookCallback callback) {
        return webhookConfigValidator.validate(callback);
    }

    public ValidationResult validate(final ProjectLink projectLink) {
        return projectLinkConfigValidator.validate(projectLink);
    }

    public ValidationResult validate(final GroupEvent event) {
        return groupEventValidator.validate(event);
    }

    public ValidationResult validate(final WebhookDefinition webhookDefinition) {
        return webhookConfigValidator.validate(webhookDefinition);
    }

    public ValidationResult groupExists(final String uuid) {
        return getGroupExistsResult(appConfig.getGroup(uuid));
    }

    public ValidationResult projectLinkExists(final String groupUuid, final String uuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(getProjectLinkExistsResult(groupDefinition, uuid));
    }

    public ValidationResult projectLinkNotExists(final String groupUuid, final String uuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(getProjectLinkNotExistsResult(groupDefinition, uuid));
    }

    public ValidationResult webhookNotExists(String groupUuid, String webhookUuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(webhookNotExistsResult(groupDefinition, webhookUuid));
    }

    public ValidationResult groupEventNotExists(String groupUuid, String eventUuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(groupEventNotExistsResult(groupDefinition, eventUuid));

    }

    public ValidationResult groupEventExists(String groupUuid, String uuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(getEventExistsResult(groupDefinition, uuid));
    }


    public ValidationResult webhookExists(String groupUuid, String uuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        return getGroupExistsResult(groupDefinition).and(getWebhookExistsResult(groupDefinition, uuid));
    }


    private ValidationResult getGroupExistsResult(GroupDefinition groupDefinition) {
        if (groupDefinition == null) {
            return ValidationResult.invalid("CALLBACK_FOUND", "Callback already exists");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult getWebhookExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getWebhooks().stream().noneMatch(webhook -> webhook.getUuid().equals(uuid))) {
            return ValidationResult.invalid("WEBHOOK_NOT_FOUND", "Webhook not found");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult getProjectLinkExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getProjectLinks().stream().noneMatch(projectLink -> projectLink.getUuid().equals(uuid))) {
            return ValidationResult.invalid("PROJECT_LINK_NOT_FOUND", "Project link not found");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult getProjectLinkNotExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getProjectLinks().stream().anyMatch(projectLink -> projectLink.getUuid().equals(uuid))) {
            return ValidationResult.invalid("PROJECT_LINK_ALREADY_EXISTS", "Project link already exists");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult webhookNotExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getWebhooks().stream().anyMatch(webhook -> webhook.getUuid().equals(uuid))) {
            return ValidationResult.invalid("WEBHOOK_ALREADY_EXISTS", "Webhook already exists");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult groupEventNotExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getEvents().stream().anyMatch(event -> event.getUuid().equals(uuid))) {
            return ValidationResult.invalid("EVENT_ALREADY_EXISTS", "Event already exists");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult getEventExistsResult(GroupDefinition groupDefinition, String uuid) {
        if (groupDefinition.getEvents().stream().noneMatch(event -> event.getUuid().equals(uuid))) {
            return ValidationResult.invalid("EVENT_NOT_FOUND", "Event not found");
        } else {
            return ValidationResult.valid();
        }
    }

    private ValidationResult getCallbackNotExistsResult(WebhookDefinition webhookDefinition, String uuid) {
        if (webhookDefinition.getCallbacks().stream().noneMatch(callback -> callback.getUuid().equals(uuid))) {
            return ValidationResult.invalid("WEBHOOK_CALLBACK_NOT_EXISTS", "Webhook callback not exists");
        } else {
            return ValidationResult.valid();
        }
    }

    public ValidationResult validate(List<GroupDefinition> groupDefinitions) {
        return ValidationResult.valid();
    }

    public ValidationResult webhookCallbackNotExists(String groupUuid, String webhookUuid, String callbackUuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        Optional<WebhookDefinition> webhookDefinition = groupDefinition.getWebhooks().stream().filter(webhook -> webhook.getUuid().equals(webhookUuid)).findFirst();
        if (webhookDefinition.isPresent()) {
            return getGroupExistsResult(groupDefinition).and(getCallbackNotExistsResult(webhookDefinition.get(), callbackUuid));
        } else {
            return ValidationResult.valid();
        }
    }

    public ValidationResult webhookCallbackExists(String groupUuid, String webhookUuid, String callbackUuid) {
        GroupDefinition groupDefinition = appConfig.getGroup(groupUuid);
        boolean callbackExists = groupDefinition.getWebhooks().stream().noneMatch(webhook -> webhook.getUuid().equals(webhookUuid));
        if (callbackExists) {
            return ValidationResult.invalid("CALLBACK_FOUND", "Callback does not exist");
        } else {
            return ValidationResult.valid();
        }
    }
}