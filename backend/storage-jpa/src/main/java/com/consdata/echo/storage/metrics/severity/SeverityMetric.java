package com.consdata.echo.storage.metrics.severity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SeverityMetric {
    Integer blocker;
    Integer critic;
    Integer major;
    Integer minor;
    Integer info;
}
