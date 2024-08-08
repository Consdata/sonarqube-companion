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

    @Override
    public OrganizationalUnit save(OrganizationalUnit unit) {
        return toOrganizationalUnit(organizationalUnitRepository.save(OrganizationalUnitEntity.of(unit)));
    }

    @Override
    public void save(List<OrganizationalUnit> unit) {
        organizationalUnitRepository.saveAll(unit.stream().map(OrganizationalUnitEntity::of).toList());
    }

    @Override
    public List<OrganizationalUnit> allUnits() {
        return organizationalUnitRepository.findAll().stream().map(OrganizationalUnitEntity::toOrganizationalUnit).toList();
    }

    @Override
    public User save(User user) {
        return null;
    }
}
