package pl.consdata.ico.sqcompanion.config.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResult {
    private String message;
    private String code;
    private boolean valid;


    public static ValidationResult valid() {
        return ValidationResult.builder().valid(true).build();
    }

    public static ValidationResult invalid(String code, String message) {
        return ValidationResult.builder().valid(false).code(code).message(message).build();
    }

    public ValidationResult and(ValidationResult validationResult) {
        if (valid) {
            return validationResult;
        } else {
            return this;
        }
    }

    public boolean isInvalid() {
        return !valid;
    }
}
