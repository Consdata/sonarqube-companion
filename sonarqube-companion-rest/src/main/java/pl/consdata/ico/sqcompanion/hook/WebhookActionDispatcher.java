package pl.consdata.ico.sqcompanion.hook;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookAction;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;

@Service
public class WebhookActionDispatcher {

    private NoImprovementWebhookAction noImprovementWebhookAction;

    public WebhookActionDispatcher(NoImprovementWebhookAction noImprovementWebhookAction) {
        this.noImprovementWebhookAction = noImprovementWebhookAction;
    }

    public ActionResponse dispatch(Webhook webhook) {
        if (webhook.getAction().getType().equals(NoImprovementWebhookAction.TYPE)) {
            return noImprovementWebhookAction.call(webhook.getGroupUuid(), (NoImprovementWebhookActionData) webhook.getAction());
        }
        return null;
    }
}