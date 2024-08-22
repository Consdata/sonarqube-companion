package com.consdata.echo.integration.sonar.reader;

import com.consdata.echo.integration.sonar.connector.Facet;
import com.consdata.echo.integration.sonar.connector.SonarConnector;
import com.consdata.echo.integration.sonar.connector.SqSearchIssuesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class ProjectFacetReader implements ItemReader<List<Facet>> {
    private final List<String> projects;
    private final LocalDate from;
    private final LocalDate to;
    private final SonarConnector sonarConnector;

    @Override
    public List<Facet> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (projects.isEmpty()) {
            return null;
        }

        String project = projects.getLast();

        List<Facet> facets = sonarConnector.search(SqSearchIssuesRequest.builder()
                .from(from)
                .to(to)
                .page(1)
                .pageSize(1)
                .facets(Arrays.asList("severities", "types", "languages", "scopes"))
                .resolved(false)
                .project(project)
                .build()).facets();

        projects.removeLast();
        return facets;
    }
}
