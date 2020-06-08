package pl.consdata.ico.sqcompanion.config.service.integrations.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.service.SettingsService;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.integrations.IntegrationsConfig;
import pl.consdata.ico.sqcompanion.integrations.ldap.configuration.LdapIntegrationConfig;

@Service
@RequiredArgsConstructor
public class LdapIntegrationConfigService {
    private final AppConfig appConfig;
    private final SettingsService settingsService;

    public LdapIntegrationConfig get() {
        if (appConfig.getIntegrations() == null || appConfig.getIntegrations().getLdap() == null) {
            return new LdapIntegrationConfig();
        } else {
            return appConfig.getIntegrations().getLdap();
        }
    }

    public ValidationResult update(LdapIntegrationConfig config) {
        if (appConfig.getIntegrations() == null) {
            appConfig.setIntegrations(new IntegrationsConfig());
        }
        appConfig.getIntegrations().setLdap(config);
        return settingsService.save();
    }
}
