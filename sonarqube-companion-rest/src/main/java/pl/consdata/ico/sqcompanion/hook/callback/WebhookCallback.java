package pl.consdata.ico.sqcompanion.hook.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostWebhookCallback.class, name = "POST"),
        @JsonSubTypes.Type(value = JSONWebhookCallback.class, name = "JSON"),
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WebhookCallback {
    @JsonProperty("type")
    public String type;
    private String uuid;
    private String name;

    public abstract CallbackResponse call(ActionResponse response);
}
