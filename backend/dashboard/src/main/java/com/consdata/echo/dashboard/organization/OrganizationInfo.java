package com.consdata.echo.dashboard.organization;

import com.consdata.echo.configuration.organization.OrganizationProperties;
import com.consdata.echo.integration.ldap.configuration.team.Team;

import java.util.List;

public record OrganizationInfo(String name, List<Team> teams) {

    public static OrganizationInfo of(OrganizationProperties properties) {
        return new OrganizationInfo(properties.name(), properties.teams());
    }
}
