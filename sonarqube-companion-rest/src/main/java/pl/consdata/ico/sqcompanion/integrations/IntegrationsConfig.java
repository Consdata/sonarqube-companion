package pl.consdata.ico.sqcompanion.integrations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.integrations.ldap.configuration.LdapIntegrationConfig;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationsConfig {
    private LdapIntegrationConfig ldap;
}
