package com.consdata.echo.dashboard.organization;

import com.consdata.echo.api.Authorities;
import com.consdata.echo.api.Role;
import com.consdata.echo.organization.OrganizationProperties;
import com.consdata.echo.organization.OrganizationStorage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("organization")
@RestController
@RequiredArgsConstructor
public class OrganizationInfoController {

    private final OrganizationStorage organizationStorage;
    private final OrganizationProperties organizationProperties;

    @GetMapping("info")
    @PreAuthorize("hasAuthority('" + Authorities.VIEW_ALL_UNITS + "')")
    @Operation(summary = "Returns basic information about organization")
    public OrganizationInfo info() {
        return new OrganizationInfo(organizationStorage.root());
    }

}

