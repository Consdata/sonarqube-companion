package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.consdata.ico.sqcompanion.config.deserialization.*;
import pl.consdata.ico.sqcompanion.config.model.*;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Configuration
@Slf4j
public class AppConfigConfiguration {

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

    @Bean
    public AppConfig appConfig(final ObjectMapper objectMapper) throws IOException {
        final Path appConfigPath = Paths.get(appConfigFile);
        log.info("Reading app configuration from path: {}", appConfigPath);
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

        if (!appConfigPath.toFile().exists()) {
            log.info("App configuration not exist, creating default [path={}]", appConfigPath);
            objectMapper.writeValue(appConfigPath.toFile(), getDefaultAppConfig());
        }

        final AppConfig appConfig = objectMapper.readValue(appConfigPath.toFile(), AppConfig.class);
        log.info("App config loaded [appConfig={}]", appConfig);
        store(objectMapper, appConfig);
        log.info("Fixed config saved [appConfig={}]", appConfig);
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

    private void store(ObjectMapper objectMapper, AppConfig appConfig) {
        try {
            objectMapper.writeValue(Paths.get(appConfigFile).toFile(), appConfig);
        } catch (IOException e) {
            log.error("Unable to store configuration in {}", this.appConfigFile, e);
        }
    }
}
