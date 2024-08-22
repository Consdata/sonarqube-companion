package com.consdata.echo.integration.sonar.connector;

import java.util.List;

public record Facet(String property, List<FacetValue> values) {
}
