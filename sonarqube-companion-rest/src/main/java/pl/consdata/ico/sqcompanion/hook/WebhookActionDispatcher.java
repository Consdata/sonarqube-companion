package pl.consdata.ico.sqcompanion.hook;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookAction;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;

@Service
public class WebhookActionDispatcher {

    private NoImprovementWebhookAction noImprovementWebhookAction;

    public WebhookActionDispatcher(NoImprovementWebhookAction noImprovementWebhookAction) {
        this.noImprovementWebhookAction = noImprovementWebhookAction;
    }

    public void dispatch(Webhook webhook) {
        if (webhook.getAction().getType().equals(NoImprovementWebhookAction.TYPE)) {
            webhook.getCallbacks().forEach(callback -> callback.call(noImprovementWebhookAction.call(webhook.getGroupUuid(), (NoImprovementWebhookActionData) webhook.getAction())));
        }
    }

}
