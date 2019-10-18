package pl.consdata.ico.sqcompanion.hook.callback;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static pl.consdata.ico.sqcompanion.hook.util.ResolveVariables.resolveVariables;

@Slf4j
@Data
public class PostWebhookCallback extends WebhookCallback {
    private Map<String, String> body;
    private String url;

    @JsonIgnore
    private RestTemplate restTemplate = new RestTemplate();

    public PostWebhookCallback() {
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Builder
    public PostWebhookCallback(String uuid, String name, Map<String, String> body, String url) {
        super(uuid, name);
        this.body = body;
        this.url = url;
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    public PostWebhookCallback(PostWebhookCallback callback, String uuid) {
        this.setName(callback.getName());
        this.setUuid(uuid);
        this.setUrl(callback.getUrl());
        this.setBody(new HashMap<>(callback.getBody()));
    }

    @Override
    public CallbackResponse call(ActionResponse response) {
        if (response != null) {
            if (body.containsKey(response.getActionResult())) {
                restTemplate.postForLocation(url, resolveVariables(response, body.get(response.getActionResult())));
            } else {
                log.warn("Invalid response");
            }
            return CallbackResponse.builder().text(response.getActionResult()).build();
        }
        return CallbackResponse.builder().text(StringUtils.EMPTY).build();
    }
}
