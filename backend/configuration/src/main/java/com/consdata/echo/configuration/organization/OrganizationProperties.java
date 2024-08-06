package com.consdata.echo.configuration.organization;

import com.consdata.echo.integration.ldap.configuration.team.Team;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "echo.organization")
public record OrganizationProperties(String name, List<Team> teams) {
}
