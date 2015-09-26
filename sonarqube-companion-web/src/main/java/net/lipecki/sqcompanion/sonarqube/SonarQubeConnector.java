package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.rest.util.RestTemplateWithAuthentication;
import org.springframework.http.ResponseEntity;

public class SonarQubeConnector {

    private final String url;

    private final RestTemplateWithAuthentication restTemplate;

    public SonarQubeConnector(final String url, final String username, final String password) {
        this.url = url;
        this.restTemplate = new RestTemplateWithAuthentication(username, password);
    }

    public <T> ResponseEntity<T> getForEntity(final String action, final Class<T> responseClass) {
        return restTemplate.getForEntity(url + action, responseClass);
    }

}
