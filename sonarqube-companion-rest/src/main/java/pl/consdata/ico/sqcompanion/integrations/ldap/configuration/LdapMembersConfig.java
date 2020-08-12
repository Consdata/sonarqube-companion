package pl.consdata.ico.sqcompanion.integrations.ldap.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LdapMembersConfig {
    private String memberObjectClass;
    private String mailAttribute;
    private String firstNameAttribute;
    private String lastNameAttribute;
    private List<String> aliasesAttributes = new ArrayList<>();
    private String idAttribute;
}
