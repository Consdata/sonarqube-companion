package com.consdata.echo.integration.sonar.connector.facets;

import java.util.Map;

public record LanguageFacet(
        Map<String, Long> entries
) {
}
