package com.consdata.echo.storage.metrics.severity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSummaryMetricsRepository extends JpaRepository<UserSummaryMetricsEntity, String> {
}
