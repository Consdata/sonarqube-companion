package com.consdata.echo.organization;

import java.util.List;

public interface OrganizationStructureProvider {
    OrganizationalUnit getRootUnit();

    List<User> getUsers();
}
