package com.consdata.echo.integration.ldap.teams;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.test.unboundid.EmbeddedLdapServerFactoryBean;
import org.springframework.ldap.test.unboundid.LdifPopulator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TestConfiguration
public class LdapTestConfiguration {

    @Bean
    public EmbeddedLdapServerFactoryBean embeddedLdapServer() {
        EmbeddedLdapServerFactoryBean embeddedLdapServer = new EmbeddedLdapServerFactoryBean();
        embeddedLdapServer.setPartitionName("echo");
        embeddedLdapServer.setPartitionSuffix("dc=echo,dc=com");
        embeddedLdapServer.setPort(9321);
        return embeddedLdapServer;
    }

    @Bean
    public LdifPopulator populator(BaseLdapPathContextSource contextSource) {
        LdifPopulator ldifPopulator = new LdifPopulator();
        ldifPopulator.setContextSource(contextSource);
        ldifPopulator.setResource(new ClassPathResource("setup_data.ldif", getClass().getClassLoader()));
        ldifPopulator.setBase("dc=echo,dc=com");
        ldifPopulator.setClean(true);
        ldifPopulator.setDefaultBase("dc=echo,dc=com");
        return ldifPopulator;
    }

    @RestController
    @RequestMapping("/")
    public class TestController {
        @PreAuthorize("hasRole('ADMIN')")
        @GetMapping("/test")
        public void test() {
        }
    }
}
