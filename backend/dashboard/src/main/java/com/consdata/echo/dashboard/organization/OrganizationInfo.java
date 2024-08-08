package com.consdata.echo.dashboard.organization;

import com.consdata.echo.organization.OrganizationalUnit;

import java.util.List;

public record OrganizationInfo(String name, List<OrganizationalUnit> organizationalUnits) {
}
