package pl.consdata.ico.sqcompanion.hook;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pl.consdata.ico.sqcompanion.hook.action.WebhookActionData;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;
import pl.consdata.ico.sqcompanion.hook.trigger.WebhookTrigger;

import java.util.List;

@Slf4j
@Data
@Builder
public class Webhook {
    private WebhookActionData action;
    private WebhookTrigger trigger;
    private List<WebhookCallback> callbacks;
    private String groupUuid;
}
