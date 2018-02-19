package pl.consdata.ico.sqcompanion.hook.callback;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

@Slf4j
@Data
public class PostWebhookCallback implements WebhookCallback {
    private String body;
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void call(ActionResponse response) {
        if (response != null) {
            restTemplate.postForLocation(url, body);
        }
    }
}
