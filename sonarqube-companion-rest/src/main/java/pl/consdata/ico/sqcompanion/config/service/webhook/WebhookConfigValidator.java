package pl.consdata.ico.sqcompanion.config.service.webhook;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;
import pl.consdata.ico.sqcompanion.hook.action.WebhookActionData;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;
import pl.consdata.ico.sqcompanion.hook.trigger.CronWebhookTrigger;
import pl.consdata.ico.sqcompanion.hook.trigger.RestWebhookTrigger;
import pl.consdata.ico.sqcompanion.hook.trigger.WebhookTrigger;

import java.util.List;

@Service
public class WebhookConfigValidator {
    public ValidationResult validate(WebhookDefinition webhook) {
        return ValidationResult.valid()
                .and(validateAction(webhook.getAction()))
                .and(validateTrigger(webhook.getTrigger()))
                .and(validateCallbacks(webhook.getCallbacks()));
    }

    public ValidationResult validate(WebhookCallback callback) {
        if (StringUtils.isBlank(callback.getName())) {
            return ValidationResult.invalid("EMPTY_NAME", "Name cannot be empty");
        }

        if (callback instanceof PostWebhookCallback) {
            PostWebhookCallback postWebhookCallback = (PostWebhookCallback) callback;
            return validatePostWebhookCallback(postWebhookCallback);
        }

        if (callback instanceof JSONWebhookCallback) {
            JSONWebhookCallback jsonWebhookCallback = (JSONWebhookCallback) callback;
            return validateJsonWebhookCallback(jsonWebhookCallback);
        }

        return ValidationResult.valid();
    }

    private ValidationResult validateJsonWebhookCallback(JSONWebhookCallback jsonWebhookCallback) {
        return ValidationResult.valid();

    }

    public ValidationResult validatePostWebhookCallback(PostWebhookCallback postWebhookCallback) {
        if (StringUtils.isBlank(postWebhookCallback.getUrl())) {
            return ValidationResult.invalid("EMPTY_URL", "URL cannot be empty!");
        }
        return ValidationResult.valid();
    }

    public ValidationResult validateCronTrigger(CronWebhookTrigger trigger) {
        return ValidationResult.valid();
    }

    public ValidationResult validateRestTrigger(RestWebhookTrigger trigger) {
        return ValidationResult.valid();
    }

    public ValidationResult validateNoImporvementAction(NoImprovementWebhookActionData action) {
        return ValidationResult.valid();
    }

    public ValidationResult validateTrigger(WebhookTrigger trigger) {
        if (trigger instanceof CronWebhookTrigger) {
            CronWebhookTrigger cronWebhookTrigger = (CronWebhookTrigger) trigger;
            validateCronTrigger(cronWebhookTrigger);
        }

        if (trigger instanceof RestWebhookTrigger) {
            RestWebhookTrigger restWebhookTrigger = (RestWebhookTrigger) trigger;
            validateRestTrigger(restWebhookTrigger);
        }
        return ValidationResult.invalid("WEBHOOK_TRIGGER", "Unknown webhook trigger");
    }

    public ValidationResult validateAction(WebhookActionData actionData) {
        if (actionData instanceof NoImprovementWebhookActionData) {
            NoImprovementWebhookActionData noImprovementWebhookActionData = (NoImprovementWebhookActionData) actionData;
            validateNoImporvementAction(noImprovementWebhookActionData);
        }
        return ValidationResult.invalid("WEBHOOK_ACTION", "Unknown webhook action");
    }

    public ValidationResult validateCallbacks(List<WebhookCallback> callbacks) {
        return callbacks.stream()
                .map(this::validate)
                .reduce((i, a) -> a.and(i))
                .orElse(ValidationResult.valid());
    }
}
