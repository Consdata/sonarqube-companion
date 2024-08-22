package com.consdata.echo.storage.metrics.severity;

import com.consdata.echo.issues.UserMetric;
import com.consdata.echo.issues.UserMetricsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserMetricsStorage implements UserMetricsStorage {

    private final UserSummaryMetricsRepository severityMetricsRepository;

    @Override
    public void saveUserSeverityMetrics(UserMetric summary) {
        severityMetricsRepository.save(UserSummaryMetricsEntity.of(summary));
    }

    @Override
    public void saveUserSeverityMetricsDiff(UserMetric diff) {

    }
}
