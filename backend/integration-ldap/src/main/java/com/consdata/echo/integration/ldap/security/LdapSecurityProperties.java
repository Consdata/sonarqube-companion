package com.consdata.echo.integration.ldap.security;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record LdapSecurityProperties(
        String userSearchBase,
        @NotBlank String userSearchFilter,
        @DefaultValue("false") boolean enabled,
        @NotBlank String groupSearchFilter,
        String groupSearchBase,
        @DefaultValue("ROLE") String rolePrefix) {
}
