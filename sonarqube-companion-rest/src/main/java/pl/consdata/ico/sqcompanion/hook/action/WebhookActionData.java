package pl.consdata.ico.sqcompanion.hook.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NoImprovementWebhookActionData.class, name = "NO_IMPROVEMENT"),
})
public interface WebhookActionData {
}
