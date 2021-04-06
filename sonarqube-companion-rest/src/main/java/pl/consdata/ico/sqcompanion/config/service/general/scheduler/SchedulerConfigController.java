package pl.consdata.ico.sqcompanion.config.service.general.scheduler;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.SchedulerConfig;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/general")
public class SchedulerConfigController extends SettingsExceptionHandler {

    private final SchedulerConfigService schedulerConfigService;

    @ApiOperation(value = "Get scheduler config",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/scheduler", produces = MediaType.APPLICATION_JSON_VALUE)
    public SchedulerConfig get() {
        log.info("Get scheduler config");
        return schedulerConfigService.get();
    }

    @ApiOperation(
            value = "Update scheduler config",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping(value = "/scheduler", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult update(@RequestBody SchedulerConfig schedulerConfig) {
        log.info("Update scheduler config {}", schedulerConfig);
        return schedulerConfigService.update(schedulerConfig);
    }

}
