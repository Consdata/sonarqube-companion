package com.consdata.echo.security.basic;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@AutoConfiguration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(BasicAuthProperties.class)
@ConditionalOnProperty(value = "echo.auth.basic.enabled", havingValue = "true")
public class BasicAuthFilter {

    @Bean
    public UserDetailsService userDetailsService(BasicAuthProperties properties) {
        return new InMemoryUserDetailsManager(ofNullable(properties.users())
                .orElse(emptyList())
                .stream()
                .map(user -> User.withUsername(user.username())
                        .password(user.password())
                        .roles(user.roles().toArray(new String[0]))
                        .build())
                .toList());
    }

    @Bean
    @Order(1)
    public SecurityFilterChain basicFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/**")
                .exceptionHandling(c -> c.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
