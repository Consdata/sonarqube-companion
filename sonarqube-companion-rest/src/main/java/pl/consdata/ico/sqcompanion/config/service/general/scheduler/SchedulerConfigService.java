package pl.consdata.ico.sqcompanion.config.service.general.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.SchedulerConfig;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfigService {
    private final AppConfig appConfig;
    private final SettingsService settingsService;

    public SchedulerConfig get() {
        log.info("Get scheduler config");
        return appConfig.getScheduler();
    }

    public ValidationResult update(SchedulerConfig schedulerConfig) {
        log.info("Update scheduler config {}", schedulerConfig);
        appConfig.setScheduler(schedulerConfig);
        return settingsService.save();
    }
}
