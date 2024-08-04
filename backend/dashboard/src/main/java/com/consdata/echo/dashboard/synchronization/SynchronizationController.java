package com.consdata.echo.dashboard.synchronization;

import com.consdata.echo.api.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("synchronization")
@RestController
@RequiredArgsConstructor
public class SynchronizationController {


    @GetMapping("schedule")
    @PreAuthorize("hasRole('" + Roles.ADMIN + "')")
    public void scheduleSynchronization() {

    }

    @GetMapping("status")
    @PreAuthorize("hasRole('" + Roles.USER + "')")
    public SynchronizationStatus status() {
        return new SynchronizationStatus(true);
    }
}
