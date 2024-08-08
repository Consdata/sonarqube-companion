package com.consdata.echo.storage;

import com.consdata.echo.organization.OrganizationalUnit;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.Collections;

@Getter
@Entity
@Table(name = "organizationalUnits") //, schema = "organization"
public class OrganizationalUnitEntity {
    @Id
    String id;
    String name;

    public static OrganizationalUnitEntity of(OrganizationalUnit ou) {
        OrganizationalUnitEntity entity = new OrganizationalUnitEntity();
        entity.id = ou.id();
        entity.name = ou.name();
        return entity;
    }

    public static OrganizationalUnit toOrganizationalUnit(OrganizationalUnitEntity entity) {
        return new OrganizationalUnit(
                entity.id,
                entity.name,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
}
