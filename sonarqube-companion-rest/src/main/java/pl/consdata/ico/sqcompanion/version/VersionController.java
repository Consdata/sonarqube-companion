package pl.consdata.ico.sqcompanion.version;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pogoma
 */
@RestController
@RequestMapping("/api/v1/version")
@Slf4j
public class VersionController {

    private final VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns application version and info about commit",
            notes = "<p>Returns application version, build timestamp, commit id and branch.</p>" +
                    "<p><b>Note</b> This service won't return proper values in development mode</p>"
    )
    public ApplicationVersion getVersion() {
        return versionService.getApplicationVersion();
    }

}
