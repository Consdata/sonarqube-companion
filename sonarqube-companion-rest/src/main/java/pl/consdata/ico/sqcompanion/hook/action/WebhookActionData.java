package pl.consdata.ico.sqcompanion.hook.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NoImprovementWebhookActionData.class, name = "NO_IMPROVEMENT"),
})
@Data
public abstract class WebhookActionData {
    @JsonProperty("type")
    public String type;
}
