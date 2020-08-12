package pl.consdata.ico.sqcompanion.members;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.Member;
import pl.consdata.ico.sqcompanion.integrations.ldap.configuration.LdapIntegrationConfig;
import pl.consdata.ico.sqcompanion.members.integrations.ldap.LdapMembersIntegration;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class MembersIntegrations implements MembersProvider {
    private final LdapMembersIntegration ldapMembersIntegration;
    private final MemberRepository memberRepository;
    private final AppConfig appConfig;

    public List<Member> getMembers() {
        if (ldapIntegrationEnabled()) {
            return ldapMembersIntegration.getMembers();
        } else {
            return emptyList();
        }
    }

    private boolean ldapIntegrationEnabled() {
        return appConfig.getIntegrations() != null &&
                ofNullable(appConfig.getIntegrations().getLdap()).map(LdapIntegrationConfig::isEnabled).orElse(false);
    }

    public Map<String, Long> getSummary() {
        return Collections.singletonMap(LdapMembersIntegration.TYPE, memberRepository.countAllByRemoteAndRemoteType(true, LdapMembersIntegration.TYPE));
    }
}
