package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Możliwości rozwoju:
 * - odświeżanie automatyczne przez cron
 */
@Configuration
@Slf4j
public class AppConfigConfiguration {

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

    @Bean
    public AppConfig appConfig(final ObjectMapper objectMapper) throws IOException {
        final Path appConfigPath = Paths.get(appConfigFile);
        log.info("Reading app configuration from path: {}", appConfigPath);

        if (!appConfigPath.toFile().exists()) {
            log.info("App configuration not exist, creating default [path={}]", appConfigPath);
            objectMapper.writeValue(appConfigPath.toFile(), getDefaultAppConfig());
        }

        final AppConfig appConfig = objectMapper.readValue(appConfigPath.toFile(), AppConfig.class);
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

}
