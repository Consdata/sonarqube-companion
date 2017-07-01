package pl.consdata.ico.sqcompanion.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.consdata.ico.sqcompanion.config.AppConfig;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public RepositoryService repositoryLayoutService(final AppConfig appConfig) {
        return new RepositoryService(appConfig);
    }

}
