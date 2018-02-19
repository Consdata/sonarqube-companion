package pl.consdata.ico.sqcompanion.hook.trigger;

import lombok.Data;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

@Data
public class CronWebhookTrigger implements SpringWebhookTrigger {

    private String definition;

    @Override
    public Trigger getTrigger() {
        return new  CronTrigger(definition);
    }
}
