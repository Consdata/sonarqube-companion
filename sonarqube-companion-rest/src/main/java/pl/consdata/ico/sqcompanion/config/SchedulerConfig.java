package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class SchedulerConfig {

    public static final int DEFAULT_INTERVAL = 30;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    private Long interval;
    private TimeUnit timeUnit;

    public static SchedulerConfig getDefault() {
        return SchedulerConfig.builder().build();
    }

    public Long getInterval() {
        return interval != null ? interval : DEFAULT_INTERVAL;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit != null ? timeUnit : DEFAULT_TIME_UNIT;
    }

}
