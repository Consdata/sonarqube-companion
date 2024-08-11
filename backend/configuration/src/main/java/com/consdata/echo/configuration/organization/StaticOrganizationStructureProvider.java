package com.consdata.echo.configuration.organization;

import com.consdata.echo.configuration.organization.structure.YamlOrganizationStructureParser;
import com.consdata.echo.organization.OrganizationalUnit;
import com.consdata.echo.organization.User;
import com.consdata.echo.organization.UsersProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class StaticOrganizationStructureProvider implements UsersProvider {
    private final OrganizationProperties organizationProperties;
    private OrganizationalUnit root;
    private Iterator<User> users;
    private Iterator<OrganizationalUnit> organizationalUnits;

    @PostConstruct
    public void init() {
        YamlOrganizationStructureParser parser = new YamlOrganizationStructureParser(organizationProperties.definitionPath());
        root = parser.parse();
        users = getUsers().iterator();
        organizationalUnits = Arrays.asList(root).iterator();
    }

    public OrganizationalUnit getRootUnit() {
        return root;
    }

    public List<User> getUsers() {
        return root.units().stream().map(this::getUsers).flatMap(List::stream).distinct().toList();
    }

    public OrganizationalUnit nextUnit() {
        if (organizationalUnits.hasNext()) {
            return organizationalUnits.next();
        } else {
            return null;
        }
    }

    @Override
    public User next() {
        if (users.hasNext()) {
            return users.next();
        } else {
            return null;
        }
    }

    private List<User> getUsers(OrganizationalUnit organizationalUnit) {
        List<User> users = new ArrayList<>(ofNullable(organizationalUnit.members()).orElse(new ArrayList<>()));
        ofNullable(organizationalUnit.units()).orElse(emptyList()).forEach(u -> users.addAll(getUsers(u)));
        return users;
    }

}
