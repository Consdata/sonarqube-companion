package pl.consdata.ico.sqcompanion.integrations.ldap.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapConnectionConfig {
    private String[] urls = new String[]{};
    private String userDn;
    private String password;
    private String base;
    private boolean anonymous;
}
