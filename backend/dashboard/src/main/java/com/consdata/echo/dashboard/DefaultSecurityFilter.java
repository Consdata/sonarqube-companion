package com.consdata.echo.dashboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DefaultSecurityFilter {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain actuatorFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**")
                .authorizeHttpRequests(customizer ->
                        customizer.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Order
    public SecurityFilterChain defaultFilter(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
        return http.build();
    }

}
