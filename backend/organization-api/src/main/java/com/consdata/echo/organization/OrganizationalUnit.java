package com.consdata.echo.organization;

import java.util.List;

public record OrganizationalUnit(
        String id,
        String name,
        List<User> members,
        List<OrganizationalUnit> units
) {
}
