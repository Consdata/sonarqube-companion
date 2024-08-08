package com.consdata.echo.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationalUnitRepository extends JpaRepository<OrganizationalUnitEntity, String> {
}
