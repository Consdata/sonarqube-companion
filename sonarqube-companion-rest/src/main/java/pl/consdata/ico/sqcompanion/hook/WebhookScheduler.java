package pl.consdata.ico.sqcompanion.hook;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WebhookScheduler {
    private final TaskScheduler webhookTaskScheduler;
    private final WebhookService webhookService;
    private final WebhookActionDispatcher dispatcher;
    private List<ScheduledFuture<?>> tasks = new ArrayList<>();

    private void callCallbacks(ActionResponse response, Webhook webhook) {
        webhook.getCallbacks().forEach(callback -> callback.call(response));
    }

    @PostConstruct
    public void initScheduledWebhooks() {
        List<Webhook> scheduledWebhooks = webhookService.getAllWebhooksWithTrigger(SpringWebhookTrigger.class);
        scheduledWebhooks.forEach(webhook -> {
            ScheduledFuture<?> task = webhookTaskScheduler.schedule(() -> callCallbacks(dispatcher.dispatch(webhook), webhook), getSpringTrigger(webhook));
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
        tasks.clear();
    }


}
