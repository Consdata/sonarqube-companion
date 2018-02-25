package pl.consdata.ico.sqcompanion.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;
import pl.consdata.ico.sqcompanion.hook.callback.CallbackResponse;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;
import pl.consdata.ico.sqcompanion.hook.trigger.RestWebhookTrigger;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
public class WebhookRestController {

    private List<Webhook> restWebhooks = new ArrayList<>();
    private WebhookActionDispatcher dispatcher;

    public WebhookRestController(WebhookService service, WebhookActionDispatcher dispatcher) {
        restWebhooks = service.getAllWebhooksWithTrigger(RestWebhookTrigger.class);
        this.dispatcher = dispatcher;
    }

    @RequestMapping(value = "/{endpoint}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CallbackResponse get(@PathVariable String endpoint) {
        Webhook endpointWebhook = restWebhooks.stream().filter(webhook -> ((RestWebhookTrigger) webhook.getTrigger()).getMethod().equals("GET")
                && ((RestWebhookTrigger) webhook.getTrigger()).getEndpoint().equals(endpoint)).findFirst().orElse(null);
        if (endpointWebhook != null) {
            ActionResponse response = dispatcher.dispatch(endpointWebhook);

            // return last callback response as response for trigger
            // its author responsibility to ensure that desired callback would be last in config
            CallbackResponse lastResponse = null;
            for (WebhookCallback callback : endpointWebhook.getCallbacks()) {
                lastResponse = callback.call(response);
            }
            return lastResponse;
        }

        return null;
    }
}
