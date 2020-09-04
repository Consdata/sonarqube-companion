package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.consdata.ico.sqcompanion.UnableToStoreAppConfigException;
import pl.consdata.ico.sqcompanion.config.deserialization.*;
import pl.consdata.ico.sqcompanion.config.model.*;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class AppConfigConfiguration {

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

    @Bean
    @ConditionalOnProperty(value = "config.store", havingValue = "file")
    public AppConfigStore appConfigStore() {
        return new FileAppConfigStore(appConfigFile);
    }

    @Bean
    public AppConfig appConfig(final ObjectMapper objectMapper, final AppConfigStore appConfigStore) throws UnableToReadAppConfigException, UnableToStoreAppConfigException {
        SimpleModule module = new SimpleModule();

        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                if (beanDesc.getBeanClass() == GroupDefinition.class) {
                    return new GroupDeserializer(deserializer);
                }

                if (beanDesc.getBeanClass() == WebhookDefinition.class) {
                    return new WebhookDeserializer(deserializer);
                }

                if (beanDesc.getBeanClass() == Member.class) {
                    return new MemberDeserializer(deserializer);
                }

                if (beanDesc.getBeanClass() == ServerDefinition.class) {
                    return new ServerDefinitionDeserializer(deserializer);
                }
                return deserializer;
            }
        });
        module.addDeserializer(ServerAuthentication.class, new ServerAuthenticationDeserializer());
        module.addDeserializer(GroupEvent.class, new GroupEventDeserializer());
        module.addDeserializer(ProjectLink.class, new ProjectLinkDeserializer());
        module.addDeserializer(PostWebhookCallback.class, new PostWebhookCallbackDeserializer());
        module.addDeserializer(JSONWebhookCallback.class, new JsonWebhookCallbackDeserializer());
        module.addDeserializer(NoImprovementWebhookActionData.class, new NoImprovementWebhookActionDataDeserializer());
        objectMapper.registerModule(module);

        final AppConfig appConfig = appConfigStore.read(objectMapper, getDefaultAppConfig());
        log.info("App config loaded [appConfig={}]", appConfig);
        return appConfig;
    }

    private AppConfig getDefaultAppConfig() {
        return AppConfig
                .builder()
                .scheduler(new SchedulerConfig(1L, TimeUnit.DAYS))
                .servers(new ArrayList<>())
                .rootGroup(
                        GroupDefinition
                                .builder()
                                .uuid(UUID.randomUUID().toString())
                                .name("All projects")
                                .build()
                )
                .build();
    }
}
