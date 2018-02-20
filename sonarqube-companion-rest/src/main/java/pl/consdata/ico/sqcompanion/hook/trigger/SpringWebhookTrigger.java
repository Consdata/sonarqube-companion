package pl.consdata.ico.sqcompanion.hook.trigger;

import org.springframework.scheduling.Trigger;

public interface SpringWebhookTrigger extends WebhookTrigger {
    Trigger getTrigger();
}
