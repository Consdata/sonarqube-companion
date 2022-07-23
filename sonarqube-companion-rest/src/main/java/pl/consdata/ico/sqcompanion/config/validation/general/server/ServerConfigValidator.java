package pl.consdata.ico.sqcompanion.config.validation.general.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.ServerAuthentication;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerConfigValidator {

    private final AppConfig appConfig;

    public ValidationResult validate(final ServerDefinition serverDefinition) {
        return Stream.of(
                validateId(serverDefinition),
                validateIdExistence(serverDefinition),
                validateUrl(serverDefinition),
                validateAuthMethod(serverDefinition)
        ).filter(ValidationResult::isInvalid).findFirst().orElse(ValidationResult.valid());
    }

    private ValidationResult validateAuthMethod(ServerDefinition serverDefinition) {
        if (serverDefinition.getAuthentication() != null) {
            if (serverDefinition.getAuthentication().getType().equalsIgnoreCase("token")) {
                return validateTokenAuth(serverDefinition.getAuthentication());
            }
            if (serverDefinition.getAuthentication().getType().equalsIgnoreCase("basic")) {
                return validateBasicAuth(serverDefinition.getAuthentication());
            }
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateTokenAuth(ServerAuthentication authentication) {
        if (authentication.getParams() == null
                || StringUtils.isBlank(authentication.getParams().get("token"))
        ) {
            return ValidationResult.invalid("AUTH_TOKEN_CONSTRAINT", "Insufficient authentication parameters");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateBasicAuth(ServerAuthentication authentication) {
        if (authentication.getParams() == null || StringUtils.isBlank(authentication.getParams().get("user"))) {
            return ValidationResult.invalid("AUTH_PASSWORD_CONSTRAINT", "Insufficient authentication parameters");
        }
        if (authentication.getParams() == null || StringUtils.isBlank(authentication.getParams().get("password"))) {
            return ValidationResult.invalid("AUTH_USER_CONSTRAINT", "Insufficient authentication parameters");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateUrl(ServerDefinition serverDefinition) {
        if (StringUtils.isBlank(serverDefinition.getUrl())) {
            return ValidationResult.invalid("URL_CONSTRAINT", "Server url cannot be empty!");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateId(ServerDefinition serverDefinition) {
        if (StringUtils.isBlank(serverDefinition.getId())) {
            return ValidationResult.invalid("ID_CONSTRAINT", "Server id cannot be empty!");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateIdExistence(ServerDefinition serverDefinition) {
        if (appConfig.getServers().stream().anyMatch(item -> item.getId().equals(serverDefinition.getId()) && !item.getUuid().equals(serverDefinition.getUuid()))) {
            return ValidationResult.invalid("ID_CONSTRAINT", "Server with given id already exists!");
        }
        return ValidationResult.valid();
    }
}
