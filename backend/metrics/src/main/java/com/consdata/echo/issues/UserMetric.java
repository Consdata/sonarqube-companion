package com.consdata.echo.issues;

import com.consdata.echo.issues.metrics.SeverityMetric;

import java.time.LocalDate;

public record UserMetric(
        String userId,
        String sourceId,
        LocalDate date,
        SeverityMetric severity
) {
}
