package pl.consdata.ico.sqcompanion.hook;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    private WebhookService service;
    private WebhookActionDispatcher dispatcher;

    public WebhookRestController(WebhookService service, WebhookActionDispatcher dispatcher) {
        this.service = service;
        this.dispatcher = dispatcher;
    }

    private String getRandomCallbackIdIfNotSet(WebhookCallback callback) {
        if (StringUtils.isBlank(callback.getUuid())) {
            return UUID.randomUUID().toString();
        } else {
            return callback.getUuid();
        }
    }

    @RequestMapping(value = "/trigger", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, CallbackResponse> get(@RequestParam String endpoint) {
        log.info("Webhook REST trigger request[endpoint={}]", endpoint);
        Webhook endpointWebhook = service.getAllWebhooksWithTrigger(RestWebhookTrigger.class).stream().filter(webhook -> ((RestWebhookTrigger) webhook.getTrigger()).getMethod().equals("GET")
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
