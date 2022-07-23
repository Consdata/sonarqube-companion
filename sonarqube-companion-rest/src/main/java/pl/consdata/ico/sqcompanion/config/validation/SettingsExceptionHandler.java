package pl.consdata.ico.sqcompanion.config.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public class SettingsExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationResult onSettingsError(Exception e) {
        log.error("Unexpected error", e);
        return ValidationResult.invalid("BAD_REQUEST", "Unexpected error");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationResult onSettingsError(ValidationException e) {
        log.error("Validation error", e);
        return e.getValidationResult();
    }
}

