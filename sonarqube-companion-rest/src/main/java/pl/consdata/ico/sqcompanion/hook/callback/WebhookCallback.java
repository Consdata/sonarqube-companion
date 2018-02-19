package pl.consdata.ico.sqcompanion.hook.callback;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostWebhookCallback.class, name = "POST"),
})
public interface WebhookCallback {
    void call(ActionResponse response);
}
