package com.consdata.echo.issues;

import com.consdata.echo.issues.metrics.IssueTypeMetric;
import com.consdata.echo.issues.metrics.SeverityMetric;

public record Metrics(
        SeverityMetric severity,
        IssueTypeMetric issueType
) {
}
