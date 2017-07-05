package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQIssuesSearchResponse;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQProjectsSearchResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@Service
@Slf4j
public class SonarQubeFacade {

    private final AppConfig config;
    private final RestTemplate restTemplate;

    public SonarQubeFacade(final AppConfig config, final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    // TODO: uwierzytelnianie na poziomie konfiguracji serwera + tokenem
    // TODO: obsługa stronicowania
    public List<SonarQubeProject> getProjects(final String serverId) {
        final ServerDefinition server = getServerDefinition(serverId);
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
        final SQProjectsSearchResponse projects = restTemplate.getForObject(
                String.format("%s%s", server.getUrl(), "api/projects/search"),
                SQProjectsSearchResponse.class
        );
        return projects
                .getComponents()
                .stream()
                .map(c -> SonarQubeProject
                        .builder()
                        .key(c.getKey())
                        .name(c.getName())
                        .build()
                )
                .collect(Collectors.toList());
    }

    // TODO: uwierzytelnianie na poziomie konfiguracji serwera + tokenem
    // TODO: obsługa stronicowania
    public List<SonarQubeIssue> getIssues(final String serverId, final String projectKey) {
        final ServerDefinition server = getServerDefinition(serverId);
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));
        final SQIssuesSearchResponse issues = restTemplate.getForObject(
                String.format("%s%s", server.getUrl(), "api/issues/search?projectKeys=" + projectKey),
                SQIssuesSearchResponse.class
        );
        return issues
                .getIssues()
                .stream()
                .map(i -> SonarQubeIssue
                        .builder()
                        .key(i.getKey())
                        .creationDate(i.getCreationDate())
                        .message(i.getMessage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private ServerDefinition getServerDefinition(final String serverId) {
        return config.getServers().stream().filter(s -> s.getId().equals(serverId)).findFirst().orElseThrow(() -> new RuntimeException("Can't find server for id: " + serverId));
    }

}
