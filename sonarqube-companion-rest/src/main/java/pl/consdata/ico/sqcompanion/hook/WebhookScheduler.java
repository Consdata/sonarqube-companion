package pl.consdata.ico.sqcompanion.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.hook.action.ActionResponse;
import pl.consdata.ico.sqcompanion.hook.trigger.SpringWebhookTrigger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@Profile("default")
public class WebhookScheduler {
    private TaskScheduler taskScheduler;
    private WebhookService webhookService;
    private WebhookActionDispatcher dispatcher;
    private List<ScheduledFuture<?>> tasks = new ArrayList<>();

    public WebhookScheduler(final TaskScheduler taskScheduler, WebhookService webhookService, WebhookActionDispatcher dispatcher) {
        this.taskScheduler = taskScheduler;
        this.webhookService = webhookService;
        this.dispatcher = dispatcher;
    }

    private void callCallbacks(ActionResponse response, Webhook webhook) {
        webhook.getCallbacks().forEach(callback -> callback.call(response));
    }

    @PostConstruct
    public void initScheduledWebhooksAfterAppInit() {
        List<Webhook> scheduledWebhooks = webhookService.getAllWebhooksWithTrigger(SpringWebhookTrigger.class);
        scheduledWebhooks.forEach(webhook -> {
            ScheduledFuture<?> task = taskScheduler.schedule(() -> callCallbacks(dispatcher.dispatch(webhook), webhook), getSpringTrigger(webhook));
            tasks.add(task);
        });
    }

    private Trigger getSpringTrigger(Webhook webhook) {
        return ((SpringWebhookTrigger) webhook.getTrigger()).getTrigger();
    }

    @PreDestroy
    public void cleanup() {
        tasks.forEach(task -> {
            if (task != null && !task.isCancelled()) {
                task.cancel(true);
            }
        });

    }


}
