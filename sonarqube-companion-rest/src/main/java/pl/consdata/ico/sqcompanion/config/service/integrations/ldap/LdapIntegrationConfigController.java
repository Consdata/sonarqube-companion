package pl.consdata.ico.sqcompanion.config.service.integrations.ldap;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.integrations.ldap.configuration.LdapIntegrationConfig;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/integrations/ldap")
public class LdapIntegrationConfigController {
    private final LdapIntegrationConfigService service;

    @GetMapping
    @ApiOperation(value = "Get ldap integration config",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LdapIntegrationConfig getConfig() {
        log.info("Get ldap integration config");
        return service.get();
    }


    @PostMapping
    @ApiOperation(value = "Update ldap integration config",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateConfig(@RequestBody LdapIntegrationConfig config) {
        log.info("Update ldap integration config");
        return service.update(config);
    }
}
