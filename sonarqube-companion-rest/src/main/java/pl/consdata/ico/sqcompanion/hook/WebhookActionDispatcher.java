package pl.consdata.ico.sqcompanion.hook;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.history.ViolationsHistoryService;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookAction;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;
import pl.consdata.ico.sqcompanion.hook.action.WebhookAction;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

@Service
public class WebhookActionDispatcher {

    private RepositoryService repositoryService;
    private ViolationsHistoryService violationsHistoryService;

    public WebhookActionDispatcher(RepositoryService repositoryService, ViolationsHistoryService violationsHistoryService) {
        this.repositoryService = repositoryService;
        this.violationsHistoryService = violationsHistoryService;
    }


    public void dispatch(Webhook webhook) {
        if (webhook.getAction() instanceof NoImprovementWebhookActionData) {
            WebhookAction action = new NoImprovementWebhookAction(repositoryService, violationsHistoryService);
            webhook.getCallbacks().forEach(callback -> callback.call(action.call(webhook.getGroupUuid(), webhook.getAction())));
        }
    }

}
