package com.consdata.echo.storage;

import com.consdata.echo.organization.OrganizationalUnit;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Getter
@Entity
@Table(name = "organizationalUnits") //, schema = "organization"
public class OrganizationalUnitEntity {
    @Id
    String id;
    String name;
    @OneToMany(fetch = FetchType.LAZY)
    List<OrganizationalUnitEntity> units;
    @OneToMany(fetch = FetchType.LAZY)
    List<UserEntity> members;

    public static OrganizationalUnitEntity of(OrganizationalUnit ou) {
        OrganizationalUnitEntity entity = new OrganizationalUnitEntity();
        entity.id = ou.id();
        entity.name = ou.name();
        entity.units = ofNullable(ou.units()).orElse(emptyList()).stream().map(OrganizationalUnitEntity::of).toList();
        entity.members = ofNullable(ou.members()).orElse(emptyList()).stream().map(UserEntity::of).toList();
        return entity;
    }

    public static OrganizationalUnit toOrganizationalUnit(OrganizationalUnitEntity entity) {
        return new OrganizationalUnit(
                entity.id,
                entity.name,
                ofNullable(entity.members).orElse(emptyList()).stream().map(UserEntity::toUser).toList(),
                ofNullable(entity.units).orElse(emptyList()).stream().map(OrganizationalUnitEntity::toOrganizationalUnit).toList()
        );
    }
}
