package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

import static java.util.Optional.ofNullable;

@Data
@Builder
@AllArgsConstructor
public class SchedulerConfig {

    public static final long DEFAULT_INTERVAL = 30;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    private Long interval;
    private TimeUnit timeUnit;

    public static SchedulerConfig getDefault() {
        return SchedulerConfig.builder().build();
    }

    public Long getInterval() {
        return ofNullable(interval).orElse(DEFAULT_INTERVAL);
    }

    public TimeUnit getTimeUnit() {
        return ofNullable(timeUnit).orElse(DEFAULT_TIME_UNIT);
    }

}
