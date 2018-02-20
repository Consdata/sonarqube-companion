package pl.consdata.ico.sqcompanion.hook.action;

public interface WebhookAction<T extends WebhookActionData> {
    ActionResponse call(String groupUUID, T actionData);
}
