package net.lipecki.sqcompanion.sonarqube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.ServerDefinition;
import net.lipecki.sqcompanion.sonarqube.sqapi.SQIssuesSearchResponse;
import net.lipecki.sqcompanion.sonarqube.sqapi.SQPaginatedResponse;
import net.lipecki.sqcompanion.sonarqube.sqapi.SQProjectsSearchResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@Service
@Slf4j
public class SonarQubeFacade {

    public static final int DEFAULT_PAGE_SIZE = 100;
    // SonarQube WEB Api uses 1-based page indexes
    public static final int FIRST_PAGE = 1;
    public static final String PAGING_TEMPLATE = "p=%d&ps=%d";
    public static final String SERVER_WITH_URI_TEMPLATE = "%s%s";
    private final AppConfig config;
    private final RestTemplate restTemplate;

    public SonarQubeFacade(final AppConfig config, final RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }

    public List<SonarQubeProject> getProjects(final String serverId) {
        return getForPaginatedList(
                serverId,
                "api/projects/search",
                SQProjectsSearchResponse.class,
                projects -> projects
                        .getComponents()
                        .stream()
                        .map(c -> SonarQubeProject
                                .builder()
                                .key(c.getKey())
                                .name(c.getName())
                                .build()
                        )
                        .collect(Collectors.toList())
        );
    }

    public List<SonarQubeIssue> getIssues(final String serverId, final String projectKey) {
        return getForPaginatedList(
                serverId,
                "api/issues/search?projectKeys=" + projectKey,
                SQIssuesSearchResponse.class,
                issues -> issues
                        .getIssues()
                        .stream()
                        .map(i -> SonarQubeIssue
                                .builder()
                                .key(i.getKey())
                                .creationDate(i.getCreationDate())
                                .message(i.getMessage())
                                .build()
                        )
                        .collect(Collectors.toList())
        );
    }

    private <T extends SQPaginatedResponse, R> List<R> getForPaginatedList(
            final String serverId,
            final String uri,
            final Class<T> responseClass,
            final Function<T, List<R>> dataExtractor) {
        final List<R> result = new ArrayList<>();

        final ServerDefinition server = getServerDefinition(serverId);
        restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

        T lastResponse = null;
        do {
            int pageIdx = lastResponse != null ? lastResponse.getNextPage() : FIRST_PAGE;
            lastResponse = restTemplate.getForObject(
                    String.format(
                            SERVER_WITH_URI_TEMPLATE + (uri.contains("?") ? "&" : "?") + PAGING_TEMPLATE,
                            server.getUrl(),
                            uri,
                            pageIdx,
                            DEFAULT_PAGE_SIZE
                    ),
                    responseClass
            );
            result.addAll(dataExtractor.apply(lastResponse));
        } while (lastResponse.hasMorePages());

        return result;
    }

    private ServerDefinition getServerDefinition(final String serverId) {
        return config.getServers()
                .stream()
                .filter(s -> s.getId().equals(serverId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find server for id: " + serverId));
    }

}
