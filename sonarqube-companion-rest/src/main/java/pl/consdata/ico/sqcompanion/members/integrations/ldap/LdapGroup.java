package pl.consdata.ico.sqcompanion.members.integrations.ldap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.config.model.Member;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LdapGroup {
    private String name;
    private String sqcUuid;
    private List<Member> members;
}
