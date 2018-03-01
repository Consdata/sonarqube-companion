package pl.consdata.ico.sqcompanion.hook.trigger;

import lombok.Data;

@Data
public class RestWebhookTrigger implements WebhookTrigger {
    private String method;
    private String endpoint;
}
