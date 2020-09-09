package pl.consdata.ico.sqcompanion.integrations.ldap.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapGroupsConfig {
    private String groupObjectClass;
    private String groupNameAttribute;
    private String membershipAttribute;
    private Map<String, String> groupMappings = new HashMap<>();
}
