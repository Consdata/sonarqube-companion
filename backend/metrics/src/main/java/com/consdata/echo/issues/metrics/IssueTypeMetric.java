package com.consdata.echo.issues.metrics;

public record IssueTypeMetric(
        Integer blocker,
        Integer critic,
        Integer major,
        Integer minor,
        Integer info
) {
}
