package net.lipecki.sqcompanion.sonarqube;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        LOG.info("Request for: {}", url + action);
        return restTemplate.getForEntity(url + action, responseClass);
    }

    private static final Logger LOG = LoggerFactory.getLogger(SonarQubeConnector.class);

}
