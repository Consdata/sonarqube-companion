package pl.consdata.ico.sqcompanion.config.service.project;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.validation.SettingsExceptionHandler;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings/group")
public class ProjectLinkConfigController extends SettingsExceptionHandler {

    private final ProjectLinkConfigService service;

    @ApiOperation(value = "Get group project links",
            httpMethod = "GET",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping(value = "/{groupUuid}/projectLinks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ProjectLink> getProjectLinks(@PathVariable String groupUuid) {
        log.info("Get group project links {}", groupUuid);
        return service.getProjectLinks(groupUuid);
    }

    @ApiOperation(value = "Create project link",
            httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/projectLinks/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult createProjectLink(@PathVariable String groupUuid, @RequestBody ProjectLink projectLink) {
        log.info("Create project link [{}] {}", groupUuid, projectLink);
        return service.createProjectLink(groupUuid, projectLink);
    }

    @ApiOperation(value = "Update project link",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping(value = "/{groupUuid}/projectLinks/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult updateProjectLink(@PathVariable String groupUuid, @RequestBody ProjectLink projectLink) {
        log.info("Update project link [{}] {}", groupUuid, projectLink);
        return service.updateProjectLink(groupUuid, projectLink);
    }

    @ApiOperation(value = "Delete project link",
            httpMethod = "DELETE",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @DeleteMapping(value = "/{groupUuid}/projectLinks/{projectLinkUuid}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ValidationResult deleteProjectLink(@PathVariable String groupUuid, @PathVariable String projectLinkUuid) {
        log.info("Delete project link {}/{}", groupUuid, projectLinkUuid);
        return service.deleteProjectLink(groupUuid, projectLinkUuid);
    }
}