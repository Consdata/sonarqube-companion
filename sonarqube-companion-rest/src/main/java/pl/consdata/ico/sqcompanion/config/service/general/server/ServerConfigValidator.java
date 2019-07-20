package pl.consdata.ico.sqcompanion.config.service.general.server;

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

    public ValidationResult validate(final ServerDefinition serverDefinition, boolean newServer) {
        return Stream.of(validateId(serverDefinition, newServer),
                validateUrl(serverDefinition),
                validateAuthMethod(serverDefinition),
                validateBlacklistUsers(serverDefinition)).filter(ValidationResult::isInvalid).findFirst().orElse(ValidationResult.valid());
    }

    private ValidationResult validateBlacklistUsers(ServerDefinition serverDefinition) {
        if (serverDefinition.getBlacklistUsers() != null && serverDefinition.getBlacklistUsers().stream().anyMatch(StringUtils::isBlank)) {
            return ValidationResult.invalid("BLACKLIST_CONSTRAINT", "Users blacklist contains empty entries!");
        }
        return ValidationResult.valid();
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
            return ValidationResult.invalid("AUTH_CONSTRAINT", "Insufficient authentication parameters");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateBasicAuth(ServerAuthentication authentication) {
        if (authentication.getParams() == null || StringUtils.isBlank(authentication.getParams().get("user")) || StringUtils.isBlank(authentication.getParams().get("password"))) {
            return ValidationResult.invalid("AUTH_CONSTRAINT", "Insufficient authentication parameters");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateUrl(ServerDefinition serverDefinition) {
        if (StringUtils.isBlank(serverDefinition.getUrl())) {
            return ValidationResult.invalid("URL_CONSTRAINT", "Server url cannot be empty!");
        }
        return ValidationResult.valid();
    }

    private ValidationResult validateId(ServerDefinition serverDefinition, boolean newServer) {
        if (StringUtils.isBlank(serverDefinition.getId())) {
            return ValidationResult.invalid("UUID_CONSTRAINT", "Server id cannot be empty!");
        }
        if (newServer && appConfig.getServers().stream().anyMatch(server -> server.getId().equals(serverDefinition.getId()))) {
            return ValidationResult.invalid("UUID_CONSTRAINT", "Server id is not unique!");
        }
        return ValidationResult.valid();
    }
}
