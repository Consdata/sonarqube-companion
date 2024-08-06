package com.consdata.echo.integration.ldap;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.web.SecurityFilterChain;

@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
public class Config {
//    @Bean
//    AuthenticationManager authenticationManager() {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(new DefaultSpringSecurityContextSource("ldap://172.19.121.15:389"));
//        factory.setUserSearchFilter("(&(objectClass=user)(userPrincipalName={0})(memberOf=CN=DEV_TESTERS,CN=Users,DC=consdata,DC=pl))");
//        return factory.createAuthenticationManager();
//    }
//
//
//    @Bean
//    @Order(1)
//    SecurityFilterChain ldapFilter(HttpSecurity http) throws Exception {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(new DefaultSpringSecurityContextSource("ldap://172.19.121.15:389"));
//        factory.setUserSearchFilter("(&(objectClass=user)(userPrincipalName={0})(memberOf=CN=DEV_TESTERS,CN=Users,DC=consdata,DC=pl))");
//        http.csrf(AbstractHttpConfigurer::disable)
//                .securityMatcher("/")
//                .authenticationManager(factory.createAuthenticationManager())
//                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
//        return http.build();
//    }

}
