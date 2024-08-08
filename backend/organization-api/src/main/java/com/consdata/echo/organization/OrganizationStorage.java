package com.consdata.echo.organization;

import java.util.List;

public interface OrganizationStorage {

    OrganizationalUnit save(OrganizationalUnit unit);

    void save(List<OrganizationalUnit> unit);

    List<OrganizationalUnit> allUnits();

    User save(User user);

}
