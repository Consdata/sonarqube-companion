package pl.consdata.ico.sqcompanion.config.model;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.hook.action.WebhookActionData;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;
import pl.consdata.ico.sqcompanion.hook.trigger.WebhookTrigger;

import java.util.List;

@Data
@Builder
public class WebhookDefinition {
    private String uuid;

    private String name;

    private WebhookActionData action;

    private WebhookTrigger trigger;

    private List<WebhookCallback> callbacks;
}