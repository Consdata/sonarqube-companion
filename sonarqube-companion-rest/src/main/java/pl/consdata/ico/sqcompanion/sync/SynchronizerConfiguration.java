package pl.consdata.ico.sqcompanion.sync;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.consdata.ico.sqcompanion.config.AppConfig;

@Configuration
public class SynchronizerConfiguration {

    @Bean
    public CronSynchronizer cronSynchronizer(final AppConfig appConfig) {
        return new CronSynchronizer(appConfig);
    }

}
