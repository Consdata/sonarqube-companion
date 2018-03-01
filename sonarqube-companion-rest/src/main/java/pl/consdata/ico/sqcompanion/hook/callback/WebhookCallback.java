package pl.consdata.ico.sqcompanion.hook.callback;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostWebhookCallback.class, name = "POST"),
        @JsonSubTypes.Type(value = JSONWebhookCallback.class, name = "JSON"),
})
@Data
public abstract class WebhookCallback {
    private String id;
    public abstract CallbackResponse call(ActionResponse response);
}
