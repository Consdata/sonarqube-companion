package com.consdata.echo.storage.metrics.severity;


import com.consdata.echo.issues.UserMetric;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userSummaryMetrics")
public class UserSummaryMetricsEntity {
    @Id
    String id;
    String userId;
    LocalDate date;
    String sourceId;
    @Embedded
    SeverityMetric severity;


    public static UserSummaryMetricsEntity of(UserMetric userMetric) {
        UserSummaryMetricsEntity entity = new UserSummaryMetricsEntity();
        entity.id = String.format("%s:%s:%s", userMetric.sourceId(), userMetric.userId(), userMetric.date().toString());
        entity.userId = userMetric.userId();
        entity.sourceId = userMetric.sourceId();
        entity.date = userMetric.date();
        entity.severity = SeverityMetric.builder()
                .blocker(userMetric.severity().blocker())
                .critic(userMetric.severity().critic())
                .major(userMetric.severity().major())
                .minor(userMetric.severity().minor())
                .info(userMetric.severity().info())
                .build();
        return entity;
    }
}
