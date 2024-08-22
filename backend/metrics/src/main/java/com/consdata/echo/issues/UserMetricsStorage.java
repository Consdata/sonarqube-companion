package com.consdata.echo.issues;

public interface UserMetricsStorage {
    void saveUserSeverityMetrics(UserMetric summary);

    void saveUserSeverityMetricsDiff(UserMetric summary);
}
