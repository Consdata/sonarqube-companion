package pl.consdata.ico.sqcompanion.hook.trigger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CronWebhookTrigger.class, name = "CRON"),
        @JsonSubTypes.Type(value = RestGetWebhookTrigger.class, name = "GET"),
})
public interface WebhookTrigger {
}
