package pl.consdata.ico.sqcompanion.integrations.ldap.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapIntegrationConfig {
    private LdapConnectionConfig connection = new LdapConnectionConfig();
    private boolean enabled = false;
    private LdapMembersConfig members = new LdapMembersConfig();
    private LdapGroupsConfig groups = new LdapGroupsConfig();
}