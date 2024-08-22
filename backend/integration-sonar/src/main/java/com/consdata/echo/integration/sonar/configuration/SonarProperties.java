package com.consdata.echo.integration.sonar.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

@ConfigurationProperties(prefix = "echo.integration.sonar")
public record SonarProperties(
        Set<SonarServerProperties> servers,
        FilterProperties filter,
        List<String> userAliases
) {

    public String serverUrl(String id) {
        return servers().stream()
                .filter(s -> id.equals(s.id()))
                .findFirst()
                .orElseThrow(() -> new UndefinedServerException(id))
                .url();
    }

    public SonarAuthorizationProperties serverAuth(String id) {
        return servers().stream()
                .filter(s -> id.equals(s.id()))
                .findFirst()
                .orElseThrow(() -> new UndefinedServerException(id))
                .auth();

    }
}
