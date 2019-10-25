package pl.consdata.ico.sqcompanion.hook.action;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class NoImprovementWebhookActionData extends WebhookActionData {

    @JsonProperty("period")
    private String period;
    @JsonProperty("severity")
    private List<String> severity;

    @Builder
    public NoImprovementWebhookActionData(String period, List<String> severity) {
        this.setPeriod(period);
        this.setSeverity(severity);
    }

    enum Period {DAILY, WEEKLY, MONTHLY}

}
