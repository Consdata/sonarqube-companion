package com.consdata.echo.integration.sonar.connector;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SonarConnector {
    private final RestClient restClient;

    public SonarConnector(@Qualifier("sonarRestClient") RestClient restClient) {
        this.restClient = restClient;
    }


    public SqSearchIssuesResponse search(String serverId, int pageSize, int pageNumber) {
        return restClient.get()
                .uri(ub -> ub.path("api/issues/search")
                        .queryParam("ps", pageSize)
                        .queryParam("p", pageNumber)
                        .build()
                ).retrieve()
                .body(SqSearchIssuesResponse.class);
    }

}
