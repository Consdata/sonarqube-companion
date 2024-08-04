package com.consdata.echo.dashboard.organization;

import com.consdata.echo.configuration.organization.OrganizationProperties;

public record OrganizationInfo(String name) {

    public static OrganizationInfo of(OrganizationProperties properties) {
        return new OrganizationInfo(properties.name());
    }
}
