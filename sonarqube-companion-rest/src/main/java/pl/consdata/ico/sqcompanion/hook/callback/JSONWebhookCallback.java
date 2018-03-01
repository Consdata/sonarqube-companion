package pl.consdata.ico.sqcompanion.hook.callback;

import lombok.Data;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

import java.util.Map;

import static pl.consdata.ico.sqcompanion.hook.util.ResolveVariables.resolveVariables;

@Data
public class JSONWebhookCallback extends WebhookCallback {
    private Map<String, String> body;

    @Override
    public CallbackResponse call(ActionResponse response) {
        return CallbackResponse.builder().text(resolveVariables(response, body.get(response.getActionResult()))).build();
    }

}
