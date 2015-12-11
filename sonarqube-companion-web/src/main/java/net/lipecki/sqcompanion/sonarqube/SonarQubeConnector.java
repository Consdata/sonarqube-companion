package net.lipecki.sqcompanion.sonarqube;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SonarQubeConnector {

    private final String url;

    private final RestTemplate restTemplate;

    public SonarQubeConnector(final String url, final RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> getForEntity(final String action, final Class<T> responseClass) {
        return restTemplate.getForEntity(url + action, responseClass);
    }

}
