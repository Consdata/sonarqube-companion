package net.lipecki.sqcompanion.sonarqube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SonarQubeInjector {

    @Bean
    public SonarQubeMetricCollector sonarQubeMetricCollector(final SonarQubeConnector sonarQubeConnector) {
        return new SonarQubeMetricCollector(sonarQubeConnector);
    }

    @Bean
    public SonarQubeMetricService sonarQubeMetricService(final SonarQubeMetricCollector sonarQubeMetricCollector) {
        return new SonarQubeMetricService(sonarQubeMetricCollector);
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
