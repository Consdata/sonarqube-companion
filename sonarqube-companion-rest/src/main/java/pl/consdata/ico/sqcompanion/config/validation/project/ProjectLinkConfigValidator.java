package pl.consdata.ico.sqcompanion.config.validation.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.DirectProjectLink;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.model.ProjectLinkType;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.project.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectLinkConfigValidator {

    private final AppConfig appConfig;
    private final ProjectService projectService;

    public ValidationResult validate(ProjectLink projectLink) {
        if (appConfig.getServers().stream().noneMatch(serverDefinition -> serverDefinition.getId().equals(projectLink.getServerId()))) {
            return ValidationResult.invalid("SERVER_ID_NOT_EXISTS", "Server with given id is not defined!");
        }
        if (ProjectLinkType.DIRECT.equals(projectLink.getType())) {
            return validateDirectLink(projectLink);
        }
        return ValidationResult.valid();
    }


    private ValidationResult validateDirectLink(ProjectLink projectLink) {
        DirectProjectLink directProjectLink = DirectProjectLink.of(projectLink);
        if (projectService.getProjects(projectLink.getServerId())
                .stream()
                .noneMatch(p -> p.getKey().equals(directProjectLink.getLink()))) {
            return ValidationResult.invalid("UNRESOLVABLE_LINK", "Can't find project by direct project link");
        }


        return ValidationResult.valid();
    }
}
