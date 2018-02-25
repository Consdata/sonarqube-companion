package pl.consdata.ico.sqcompanion.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.WebhookDefinition;
import pl.consdata.ico.sqcompanion.hook.trigger.WebhookTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebhookService {
    private AppConfig config;
    private List<Webhook> webhooks = new ArrayList<>();

    public WebhookService(AppConfig config) {
        this.config = config;
        syncWebhooks();
    }

    private void syncWebhooks() {
        agregateWebhooks(config.getRootGroup());
    }

    private List<Webhook> buildWebhooks(GroupDefinition group) {
        return group.getWebhooks().stream().map(webhookDef -> buildWebhook(webhookDef, group.getUuid())).collect(Collectors.toList());
    }

    private void agregateWebhooks(final GroupDefinition group) {
        try {
            group.getGroups().forEach(this::agregateWebhooks);
            webhooks.addAll(buildWebhooks(group));
        } catch (final Exception exception) {
            log.error("Can't sync group details [group={}]", group, exception);
        }
    }

    private Webhook buildWebhook(final WebhookDefinition definition, String uuid) {
        return Webhook.builder()
                .groupUuid(uuid)
                .action(definition.getAction())
                .trigger(definition.getTrigger())
                .callbacks(definition.getCallbacks())
                .build();
    }

    public List<Webhook> getAllWebhooksWithTrigger(Class<? extends WebhookTrigger> clazz) {
        return webhooks.stream().filter(webhook -> clazz.isInstance(webhook.getTrigger())).collect(Collectors.toList());
    }
}
