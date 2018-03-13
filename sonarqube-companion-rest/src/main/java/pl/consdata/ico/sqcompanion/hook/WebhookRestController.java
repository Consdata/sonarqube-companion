package pl.consdata.ico.sqcompanion.hook;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;
import pl.consdata.ico.sqcompanion.hook.callback.CallbackResponse;
import pl.consdata.ico.sqcompanion.hook.callback.WebhookCallback;
import pl.consdata.ico.sqcompanion.hook.trigger.RestWebhookTrigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
public class WebhookRestController {

    private List<Webhook> restWebhooks;
    private WebhookActionDispatcher dispatcher;

    public WebhookRestController(WebhookService service, WebhookActionDispatcher dispatcher) {
        restWebhooks = service.getAllWebhooksWithTrigger(RestWebhookTrigger.class);
        this.dispatcher = dispatcher;
    }

    private String getRandomCallbackIdIfNotSet(WebhookCallback callback) {
        if (StringUtils.isBlank(callback.getId())) {
            return UUID.randomUUID().toString();
        } else {
            return callback.getId();
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/{endpoint}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, CallbackResponse> get(@PathVariable String endpoint) {
        Webhook endpointWebhook = restWebhooks.stream().filter(webhook -> ((RestWebhookTrigger) webhook.getTrigger()).getMethod().equals("GET")
                && ((RestWebhookTrigger) webhook.getTrigger()).getEndpoint().equals(endpoint)).findFirst().orElse(null);
        if (endpointWebhook != null) {
            ActionResponse response = dispatcher.dispatch(endpointWebhook);

            Map<String, CallbackResponse> callbackResponses = new HashMap<>();
            endpointWebhook.getCallbacks().forEach(callback -> callbackResponses.put(getRandomCallbackIdIfNotSet(callback), callback.call(response)));
            return callbackResponses;
        }

        return null;
    }
}
