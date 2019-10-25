package pl.consdata.ico.sqcompanion.config.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class SettingsExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ValidationResult onSettingsError(Exception e) {
        log.error("Unexpected error", e);
        return ValidationResult.invalid("BAD_REQUEST", "Unexpected error");
    }
}
