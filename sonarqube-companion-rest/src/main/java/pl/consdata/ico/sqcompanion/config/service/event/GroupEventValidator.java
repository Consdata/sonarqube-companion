package pl.consdata.ico.sqcompanion.config.service.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static pl.consdata.ico.sqcompanion.config.validation.ValidationResult.invalid;
import static pl.consdata.ico.sqcompanion.config.validation.ValidationResult.valid;

@Service
@Slf4j
public class GroupEventValidator {

    public ValidationResult validate(GroupEvent event) {
        ValidationResult validationResult = validateType(event);
        if (event.getType().toLowerCase().equals(GroupEvent.TYPE.PERIOD.name().toLowerCase())) {
            validationResult = validatePeriodType(event);
        }
        if (event.getType().toLowerCase().equals(GroupEvent.TYPE.DATE.name().toLowerCase())) {
            validationResult = validateDateType(event);
        }
        return validationResult;
    }

    private ValidationResult validateType(GroupEvent event) {
        if (Arrays.stream(GroupEvent.TYPE.values()).anyMatch(type -> type.name().toLowerCase().equals(event.getType().toLowerCase()))) {
            return valid();
        } else {
            return invalid("TYPE_CONSTRAINT", String.format("Unknown type: %s", event.getType()));
        }
    }

    private ValidationResult validatePeriodType(GroupEvent groupEvent) {
        if (!groupEvent.getData().containsKey("startDate")) {
            return invalid("PARAMS_CONSTRAINT", "startDate parameter not found");
        }
        try {
            DateTimeFormatter.ISO_DATE.parse(String.valueOf(groupEvent.getData().get("startDate")));
        } catch (DateTimeParseException e) {
            return invalid("PARAMS_CONSTRAINT", "Invalid startDate format, try yyyy-mm-dd");
        }
        if (!groupEvent.getData().containsKey("endDate")) {
            return invalid("PARAMS_CONSTRAINT", "endDate parameter not found");
        }
        try {
            DateTimeFormatter.ISO_DATE.parse(String.valueOf(groupEvent.getData().get("endDate")));
        } catch (DateTimeParseException e) {
            return invalid("PARAMS_CONSTRAINT", "Invalid endDate format, try yyyy-mm-dd");
        }

        if (isStartDateAfterEndDate(String.valueOf(groupEvent.getData().get("startDate")), String.valueOf(groupEvent.getData().get("endDate")))) {
            return invalid("PARAMS_CONSTRAINT", "Start date is after end date");
        }

        if (isStartDateEqualToEndDate(String.valueOf(groupEvent.getData().get("startDate")), String.valueOf(groupEvent.getData().get("endDate")))) {
            return invalid("PARAMS_CONSTRAINT", "Start date is equal to end date. Use DATE event type instead");
        }
        return valid();
    }

    private boolean isStartDateAfterEndDate(String startDateRaw, String endDateRaw) {
        LocalDate startDate = LocalDate.parse(startDateRaw, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(endDateRaw, DateTimeFormatter.ISO_DATE);
        return startDate.isAfter(endDate);
    }

    private boolean isStartDateEqualToEndDate(String startDateRaw, String endDateRaw) {
        LocalDate startDate = LocalDate.parse(startDateRaw, DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(endDateRaw, DateTimeFormatter.ISO_DATE);
        return startDate.isEqual(endDate);
    }

    private ValidationResult validateDateType(GroupEvent groupEvent) {
        if (!groupEvent.getData().containsKey("date")) {
            return invalid("PARAMS_CONSTRAINT", "date parameter not found");
        }
        try {
            DateTimeFormatter.ISO_DATE.parse(String.valueOf(groupEvent.getData().get("date")));
        } catch (DateTimeParseException e) {
            return invalid("PARAMS_CONSTRAINT", "Invalid date format, try yyyy-mm-dd");
        }
        return valid();
    }
}
