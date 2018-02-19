package pl.consdata.ico.sqcompanion.hook.callback;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

import java.nio.charset.Charset;
import java.util.Map;

@Slf4j
@Data
public class PostWebhookCallback implements WebhookCallback {
    private Map<String, String> body;
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    public PostWebhookCallback(){
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }
    @Override
    public void call(ActionResponse response) {
        if (response != null) {
            if (body.containsKey(response.getActionResult())) {
                restTemplate.postForLocation(url, body.get(response.getActionResult()));
            } else {
                log.warn("Invalid response");
            }
        }
    }
}
