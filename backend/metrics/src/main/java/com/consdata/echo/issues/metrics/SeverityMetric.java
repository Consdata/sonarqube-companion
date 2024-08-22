package com.consdata.echo.issues.metrics;

public record SeverityMetric(
        Integer blocker,
        Integer critic,
        Integer major,
        Integer minor,
        Integer info
) {
}
