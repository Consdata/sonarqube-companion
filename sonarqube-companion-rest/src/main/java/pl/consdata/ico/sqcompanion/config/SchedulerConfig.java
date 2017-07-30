package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class SchedulerConfig {

    private Long interval;
    private TimeUnit timeUnit;

    public static SchedulerConfig getDefault() {
        return SchedulerConfig.builder().build();
    }

    public Long getInterval() {
        return interval != null ? interval : 30;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit != null ? timeUnit : TimeUnit.MINUTES;
    }

}
