package pl.consdata.ico.sqcompanion.hook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
public class WebhookConfiguration {
    @Autowired
    private WebhookService webhookService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private WebhookActionDispatcher webhookActionDispatcher;

    @Bean
    public WebhookScheduler webhookScheduler(){
        return new WebhookScheduler(taskScheduler, webhookService, webhookActionDispatcher);
    }
}
