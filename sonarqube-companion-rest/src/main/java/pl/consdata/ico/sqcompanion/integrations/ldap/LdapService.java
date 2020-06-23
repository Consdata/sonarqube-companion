package pl.consdata.ico.sqcompanion.integrations.ldap;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.integrations.ldap.configuration.LdapConnectionConfig;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapService {
    private final AppConfig appConfig;
    private final LdapTemplate ldapTemplate;

    public LdapTemplate getLdapTemplate() {
        ldapTemplate.setContextSource(ldapContextSource());
        return ldapTemplate;
    }

    private ContextSource ldapContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        LdapConnectionConfig config = appConfig.getIntegrations().getLdap().getConnection();
        ldapContextSource.setUrls(config.getUrls());
        ldapContextSource.setBase(config.getBase());
        ldapContextSource.setPooled(true);
        if (config.isAnonymous()) {
            ldapContextSource.setAnonymousReadOnly(true);
        } else {
            ldapContextSource.setUserDn(config.getUserDn());
            ldapContextSource.setPassword(config.getPassword());
        }
        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }

    public <T> List<T> search(ContainerCriteria objectClass, AttributesMapper<T> memberAttributesMapper) {
        return getLdapTemplate().search(objectClass, memberAttributesMapper);
    }
}
