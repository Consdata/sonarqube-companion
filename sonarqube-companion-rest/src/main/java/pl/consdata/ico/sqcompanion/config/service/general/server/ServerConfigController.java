package pl.consdata.ico.sqcompanion.config.service.general.server;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/general/server")
public class ServerConfigController extends SettingsExceptionHandler {

    private final ServerConfigService serverConfigService;

    @ApiOperation(value = "Update server definition",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult update(@RequestBody ServerDefinition serverDefinition) {
        log.info("Update server definition {}", serverDefinition);
        return serverConfigService.update(serverDefinition);
    }

    @ApiOperation(value = "Create server definition",
            httpMethod = "POST"
    )
    @PostMapping(value = "/create")
    public void create() {
        log.info("Create server");
        serverConfigService.create();
    }

    @ApiOperation(value = "Delete server definition",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ValidationResult delete(@PathVariable String uuid) {
        log.info("Delete server definition {}", uuid);
        return serverConfigService.delete(uuid);
    }

    @ApiOperation(value = "Get servers definitions",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServerDefinition> get() {
        log.info("Get available servers definitions");
        return serverConfigService.get();
    }

}
