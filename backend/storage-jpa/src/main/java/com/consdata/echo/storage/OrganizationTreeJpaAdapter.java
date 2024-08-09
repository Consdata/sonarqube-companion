package com.consdata.echo.storage;

import com.consdata.echo.organization.OrganizationalUnit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
public class OrganizationTreeJpaAdapter {

    public List<OrganizationalUnit> adapt(OrganizationalUnit root) {
        List<OrganizationalUnit> toSave = new ArrayList<>();

        List<OrganizationalUnit> unitsWithSubunits = root.units().stream()
                .map(this::getOrganizationalUnits)
                .flatMap(List::stream)
                .distinct().toList();

        List<OrganizationalUnit> initialUnitsWithoutSubunit = unitsWithSubunits.stream()
                .map(u -> new OrganizationalUnit(
                        u.id(),
                        u.name(),
                        u.members(),
                        emptyList()
                ))
                .toList();
        toSave.addAll(initialUnitsWithoutSubunit);
        toSave.addAll(unitsWithSubunits);
        toSave.add(root);
        return toSave;
    }

    private List<OrganizationalUnit> getOrganizationalUnits(OrganizationalUnit organizationalUnit) {
        List<OrganizationalUnit> units = new ArrayList<>(ofNullable(organizationalUnit.units()).orElse(new ArrayList<>()));
        units.add(organizationalUnit);
        ofNullable(organizationalUnit.units()).orElse(emptyList()).forEach(u -> units.addAll(getOrganizationalUnits(u)));
        return units;
    }

}
