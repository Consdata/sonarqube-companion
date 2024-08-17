package com.consdata.echo.integration.sonar.configuration;

public record SonarServerProperties(
        String id,
        String url,
        SonarAuthorizationProperties auth
) {
}
