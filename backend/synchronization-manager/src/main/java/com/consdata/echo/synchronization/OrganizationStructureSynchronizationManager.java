package com.consdata.echo.synchronization;

import com.consdata.echo.organization.OrganizationStorage;
import com.consdata.echo.organization.OrganizationStructureProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationStructureSynchronizationManager implements SynchronizationManager {

    private final OrganizationStorage organizationStorage;
    private final OrganizationStructureProvider structureProvider;

    @Override
    public void sync() {
        organizationStorage.saveUsers(structureProvider.getUsers());
        organizationStorage.saveOrganizationRoot(structureProvider.getRootUnit());
    }
}
