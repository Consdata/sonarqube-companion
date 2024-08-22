package com.consdata.echo.integration.sonar.connector;

import com.consdata.echo.integration.sonar.configuration.SonarProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.format.DateTimeFormatter;

import static java.util.Optional.ofNullable;

@Service
public class SonarConnector {
    private final RestClient restClient;
    private final SonarProperties properties;

    public SonarConnector(@Qualifier("sonarRestClient") RestClient restClient, SonarProperties properties) {
        this.restClient = restClient;
        this.properties = properties;
    }

    public SqSearchIssuesResponse search(SqSearchIssuesRequest request) {
        return restClient.get()
                .uri(properties.serverUrl(request.serverId()), ub -> ub.path("api/issues/search")
                        .queryParam("ps", request.pageSize())
                        .queryParam("p", request.page())
                        .queryParamIfPresent("s", ofNullable(request.sort()))
                        .queryParamIfPresent("createdAfter", ofNullable(request.from()).map(d -> d.format(DateTimeFormatter.ISO_DATE)))
                        .queryParamIfPresent("createdBefore", ofNullable(request.to()).map(d -> d.format(DateTimeFormatter.ISO_DATE)))
                        .queryParamIfPresent("facets", ofNullable(request.facets()).map(l -> String.join(",", l)))
                        .queryParamIfPresent("resolved", ofNullable(request.resolved()))
                        .queryParamIfPresent("componentKeys", ofNullable(request.project()))
                        .queryParamIfPresent("author", ofNullable(request.user()))
                        .build()
                ).header(HttpHeaders.AUTHORIZATION, properties.serverAuth(request.serverId()).asBasic())
                .retrieve()
                .body(SqSearchIssuesResponse.class);
    }

}

