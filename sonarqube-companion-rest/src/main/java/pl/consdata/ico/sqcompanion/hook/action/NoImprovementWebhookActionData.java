package pl.consdata.ico.sqcompanion.hook.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NoImprovementWebhookActionData extends WebhookActionData {

    @JsonProperty("period")
    private Period period;
    @JsonProperty("severity")
    private List<String> severity;

    enum Period {DAILY}

}
