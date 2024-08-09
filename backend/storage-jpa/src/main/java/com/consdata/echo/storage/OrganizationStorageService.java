package com.consdata.echo.storage;

import com.consdata.echo.organization.OrganizationStorage;
import com.consdata.echo.organization.OrganizationalUnit;
import com.consdata.echo.organization.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.consdata.echo.storage.OrganizationalUnitEntity.toOrganizationalUnit;

@Service
@RequiredArgsConstructor
public class OrganizationStorageService implements OrganizationStorage {

    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final UsersRepository usersRepository;
    private final OrganizationTreeJpaAdapter organizationTreeJpaAdapter;

    @Override
    public OrganizationalUnit saveOrganizationRoot(OrganizationalUnit unit) {
        List<OrganizationalUnit> units = organizationTreeJpaAdapter.adapt(unit);
        organizationalUnitRepository.saveAll(units.stream().map(OrganizationalUnitEntity::of).toList());
        return unit;
    }

    @Override
    public OrganizationalUnit root() {
        return toOrganizationalUnit(organizationalUnitRepository.root());
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public void saveUsers(List<User> users) {
        usersRepository.saveAll(users.stream().map(UserEntity::of).toList());
    }
}
