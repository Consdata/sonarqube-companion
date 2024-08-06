package com.consdata.echo.integration.ldap;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(LdapProperties.class)
@ConditionalOnProperty(value = "echo.integration.ldap.enabled", havingValue = "true")
public class LdapIntegrationAutoconfiguration {


    @Bean
    public BaseLdapPathContextSource ldapContextSource(LdapProperties properties) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(properties.url());
        ldapContextSource.setBase(properties.base());
        ldapContextSource.setPooled(true);
        if (properties.anonymous()) {
            ldapContextSource.setAnonymousReadOnly(true);
        } else {
            ldapContextSource.setUserDn(properties.userDn());
            ldapContextSource.setPassword(properties.password());
        }
        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(BaseLdapPathContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @ConditionalOnProperty(value = "echo.integration.ldap.security.enabled", havingValue = "true")
    @Bean("ldapAuthenticationManager")
    public AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, LdapProperties properties) {
        DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(contextSource, properties.security().groupSearchBase());
        populator.setGroupSearchFilter(properties.security().groupSearchFilter());
        populator.setRolePrefix(properties.security().rolePrefix());
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserSearchFilter(properties.security().userSearchFilter());
        factory.setUserSearchBase(properties.security().userSearchBase());
        factory.setLdapAuthoritiesPopulator(populator);
        return factory.createAuthenticationManager();
    }

}
