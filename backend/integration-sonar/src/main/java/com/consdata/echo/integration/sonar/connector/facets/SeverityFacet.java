package com.consdata.echo.integration.sonar.connector.facets;

public record SeverityFacet(
        long blockers,
        long critics,
        long majors,
        long minors,
        long infos
) {
}
