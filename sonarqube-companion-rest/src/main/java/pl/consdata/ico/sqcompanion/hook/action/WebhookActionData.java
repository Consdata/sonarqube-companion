package pl.consdata.ico.sqcompanion.hook.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NoImprovementWebhookActionData.class, name = "NO_IMPROVEMENT"),
})
@Data
@NoArgsConstructor
public abstract class WebhookActionData {
}
