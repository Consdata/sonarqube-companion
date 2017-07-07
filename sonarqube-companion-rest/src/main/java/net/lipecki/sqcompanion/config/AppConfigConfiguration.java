package net.lipecki.sqcompanion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Configuration
@Slf4j
public class AppConfigConfiguration {

    /**
     * TODO: refresh config via cron
     */

    @Bean
    public AppConfig appConfig() throws IOException {
        final Path appConfigPath = Paths.get(appConfigFile);
        log.info("Reading app configuration from path: {}", appConfigPath);

        if (!Files.exists(appConfigPath)) {
            log.info("App configuration not exist, creating default [path={}]", appConfigPath);
            new ObjectMapper().writeValue(appConfigPath.toFile(), getDefaultAppConfig());
        }

        final AppConfig appConfig = new ObjectMapper().readValue(appConfigPath.toFile(), AppConfig.class);
        log.info("App config loaded [appConfig={}]", appConfig);
        return appConfig;
    }

    private AppConfig getDefaultAppConfig() {
        return AppConfig
                .builder()
                .rootGroup(
                        GroupDefinition
                                .builder()
                                .uuid(UUID.randomUUID().toString())
                                .name("All projects")
                                .build()
                )
                .build();
    }

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

}
