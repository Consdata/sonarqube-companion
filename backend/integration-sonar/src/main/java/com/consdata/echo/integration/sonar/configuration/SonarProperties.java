package com.consdata.echo.integration.sonar.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "echo.integration.sonar")
public record SonarProperties(
        List<SonarServerProperties> servers,
        String url,
        SonarAuthorizationProperties auth
) {
}
