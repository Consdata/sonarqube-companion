package pl.consdata.ico.sqcompanion.config.validation.members;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

@Service
@RequiredArgsConstructor
public class MembersValidator {

    private final AppConfig appConfig;

    public ValidationResult memberExists(String uuid) {
        if (appConfig.getMembers().getLocal().stream().noneMatch(m -> uuid.equals(m.getUuid()))) {
            return ValidationResult.invalid("MEMBER_NOT_EXISTS", "Member does not exist");
        }
        return ValidationResult.valid();
    }

    public ValidationResult memberWithSonarIdNotExists(String sonarId) {
        if (appConfig.getMembers().getLocal().stream().anyMatch(m -> sonarId.equals(m.getSonarId()))) {
            return ValidationResult.invalid("MEMBER_ALREADY_EXISTS", "Member with sonarId: " + sonarId + " already exist");
        }
        return ValidationResult.valid();
    }
}
