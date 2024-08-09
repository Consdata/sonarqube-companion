package com.consdata.echo.dashboard;

import com.consdata.echo.organization.OrganizationalUnit;
import com.consdata.echo.organization.OrganizationStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final OrganizationStorage storage;

    // TODO POST & form
    @GetMapping
    public String elo() {
        storage.saveOrganizationRoot(new OrganizationalUnit("sad222as", "222asdas", Collections.emptyList(), Collections.emptyList()));
        return "asd22";
    }

}
