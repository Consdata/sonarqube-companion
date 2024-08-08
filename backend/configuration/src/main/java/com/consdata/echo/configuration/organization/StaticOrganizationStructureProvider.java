package com.consdata.echo.configuration.organization;

import com.consdata.echo.configuration.organization.structure.YamlOrganizationStructureParser;
import com.consdata.echo.organization.OrganizationStructureProvider;
import com.consdata.echo.organization.OrganizationalUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticOrganizationStructureProvider implements OrganizationStructureProvider {
    private final OrganizationProperties organizationProperties;

    @Override
    public List<OrganizationalUnit> getOrganizationalUnits() {
        YamlOrganizationStructureParser parser = new YamlOrganizationStructureParser(organizationProperties.definitionPath());
        return parser.parse();
    }


}
