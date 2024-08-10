package com.consdata.echo.dashboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
@Slf4j
public class LoginController {

    // TODO POST & form
    @GetMapping
    public String elo() {
        log.info("eu");
        return "asd22";
    }

}
