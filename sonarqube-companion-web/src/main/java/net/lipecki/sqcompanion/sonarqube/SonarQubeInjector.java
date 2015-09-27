package net.lipecki.sqcompanion.sonarqube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SonarQubeInjector {

    @Bean
    public SonarQubeServiceOld sonarQubeMetricCollector(final SonarQubeConnector sonarQubeConnector) {
        return new SonarQubeServiceOld(sonarQubeConnector);
    }

    @Bean
    public SonarQubeService sonarQubeService(final SonarQubeConnector sonarQubeConnector) {
        return new SonarQubeService(sonarQubeConnector);
    }

    @Bean
    public SonarQubeConnector sonarQubeConnector() {
        return new SonarQubeConnector(sonarQubeUrl, sonarQubeUsername, sonarQubePassword);
    }

    @Value("${sonarqube.url}")
    private String sonarQubeUrl;

    @Value("${sonarqube.username}")
    private String sonarQubeUsername;

    @Value("${sonarqube.password}")
    private String sonarQubePassword;

}
