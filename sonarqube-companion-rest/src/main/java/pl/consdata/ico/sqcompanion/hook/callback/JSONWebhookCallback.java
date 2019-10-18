package pl.consdata.ico.sqcompanion.hook.callback;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

import java.util.HashMap;
import java.util.Map;

import static pl.consdata.ico.sqcompanion.hook.util.ResolveVariables.resolveVariables;

@Data
@NoArgsConstructor
public class JSONWebhookCallback extends WebhookCallback {
    private Map<String, String> body;

    @Builder
    public JSONWebhookCallback(String uuid, String name, Map<String, String> body) {
        this.body = body;
    }

    public JSONWebhookCallback(JSONWebhookCallback callback, String uuid) {
        this.setName(callback.getName());
        this.setUuid(uuid);
        this.setBody(new HashMap<>(callback.getBody()));
    }

    @Override
    public CallbackResponse call(ActionResponse response) {
        return CallbackResponse.builder().text(resolveVariables(response, body.get(response.getActionResult()))).build();
    }

}

