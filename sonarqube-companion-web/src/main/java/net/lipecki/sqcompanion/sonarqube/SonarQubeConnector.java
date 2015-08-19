package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.rest.RestTemplateWithAuthentication;
import org.springframework.http.ResponseEntity;

public class SonarQubeConnector {

    private final String url;

    private final RestTemplateWithAuthentication restTemplate;

    public SonarQubeConnector(final String url, final String username, final String password) {
        this.url = url;
        this.restTemplate = new RestTemplateWithAuthentication(username, password);
    }

    public ResponseEntity<String> getForEntity(final String action, final Class<String> responseClass) {
        return  restTemplate.getForEntity(url + action, responseClass);
    }

}
