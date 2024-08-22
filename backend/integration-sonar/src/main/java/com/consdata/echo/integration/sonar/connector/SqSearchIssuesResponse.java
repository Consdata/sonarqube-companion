package com.consdata.echo.integration.sonar.connector;

import java.util.List;

public record SqSearchIssuesResponse(
        SqPaging paging,
        List<SqIssue> issues,
        List<Facet> facets
) {
}

