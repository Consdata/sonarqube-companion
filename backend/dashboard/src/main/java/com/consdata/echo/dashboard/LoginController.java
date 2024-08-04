package com.consdata.echo.dashboard;

import com.consdata.echo.api.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@Slf4j
public class LoginController {

    // TODO POST & form
    @GetMapping
    @PreAuthorize("hasRole('" + Roles.USER + "')")
    public String elo() {
        log.info("eu");
        return "asd22";
    }

}
