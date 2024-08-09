package com.consdata.echo.organization;

import java.util.List;

public interface OrganizationStorage {

    OrganizationalUnit saveOrganizationRoot(OrganizationalUnit unit);

    OrganizationalUnit root();

    User save(User user);

    void saveUsers(List<User> users);
}
