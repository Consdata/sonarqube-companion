package com.consdata.echo.organization;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "echo.organization")
public record OrganizationProperties(String name, String definitionPath) {
}
