package com.consdata.echo.configuration.organization;

import com.consdata.echo.configuration.organization.structure.YamlOrganizationStructureParser;
import com.consdata.echo.organization.OrganizationStructureProvider;
import com.consdata.echo.organization.OrganizationalUnit;
import com.consdata.echo.organization.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class StaticOrganizationStructureProvider implements OrganizationStructureProvider {
    private final OrganizationProperties organizationProperties;
    private OrganizationalUnit root;

    @PostConstruct
    public void init() {
        YamlOrganizationStructureParser parser = new YamlOrganizationStructureParser(organizationProperties.definitionPath());
        root = parser.parse();
    }

    @Override
    public OrganizationalUnit getRootUnit() {
        return root;
    }

    @Override
    public List<User> getUsers() {
        return root.units().stream().map(this::getUsers).flatMap(List::stream).distinct().toList();
    }

    private List<User> getUsers(OrganizationalUnit organizationalUnit) {
        List<User> users = new ArrayList<>(ofNullable(organizationalUnit.members()).orElse(new ArrayList<>()));
        ofNullable(organizationalUnit.units()).orElse(emptyList()).forEach(u -> users.addAll(getUsers(u)));
        return users;
    }

}
