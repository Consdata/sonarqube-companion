package com.consdata.echo.integration.ldap;

import com.consdata.echo.integration.ldap.security.LdapSecurityProperties;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties("echo.integration.ldap")
public record LdapProperties(
        @DefaultValue("false") boolean enabled,
        @URL @NotBlank String url,
        @NotBlank String base,
        @DefaultValue("true") boolean anonymous,
        String userDn,
        String password,
        LdapSecurityProperties security) {
}
