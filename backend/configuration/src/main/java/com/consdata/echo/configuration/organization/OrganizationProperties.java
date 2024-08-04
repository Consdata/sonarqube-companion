package com.consdata.echo.configuration.organization;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "echo.organization")
public record OrganizationProperties(String name) {
}
