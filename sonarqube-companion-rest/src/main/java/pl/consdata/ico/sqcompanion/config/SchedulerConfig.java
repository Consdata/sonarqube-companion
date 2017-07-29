package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@Builder
public class SchedulerConfig {

    private Long interval;

    private TimeUnit timeUnit;
}
