package pl.consdata.ico.sqcompanion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.consdata.ico.sqcompanion.config.git.GitProperties;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties(GitProperties.class)
public class SonarqubeCompanionRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarqubeCompanionRestApplication.class, args);
    }

}
