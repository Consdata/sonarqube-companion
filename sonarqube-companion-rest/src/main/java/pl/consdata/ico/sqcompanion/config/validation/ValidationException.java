package pl.consdata.ico.sqcompanion.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationException extends RuntimeException{
    @Getter
    private ValidationResult validationResult;
}
