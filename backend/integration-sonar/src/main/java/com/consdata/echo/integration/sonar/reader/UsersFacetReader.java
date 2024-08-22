package com.consdata.echo.integration.sonar.reader;

import com.consdata.echo.integration.sonar.connector.Facet;
import com.consdata.echo.integration.sonar.connector.SonarConnector;
import com.consdata.echo.integration.sonar.connector.SqSearchIssuesRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UsersFacetReader implements ItemReader<List<Facet>> {

    private final List<String> users;
    private final LocalDate from;
    private final LocalDate to;
    private final SonarConnector sonarConnector;

    @Override
    public List<Facet> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (users.isEmpty()) {
            return null;
        }

        String user = users.getFirst();
        List<Facet> facets = sonarConnector.search(SqSearchIssuesRequest.builder()
                .serverId("sq9")
                .from(from)
                .to(to)
                .page(1)
                .pageSize(1)
                .facets(Arrays.asList("severities", "types", "languages", "scopes"))
                .resolved(false)
                .user(user)
                .build()).facets();

        users.removeFirst();
        return facets;
    }

}
