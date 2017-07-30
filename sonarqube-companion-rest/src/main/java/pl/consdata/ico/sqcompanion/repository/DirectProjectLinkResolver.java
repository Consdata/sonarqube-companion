package pl.consdata.ico.sqcompanion.repository;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.config.DirectProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.project.ProjectEntity;
import pl.consdata.ico.sqcompanion.project.ProjectService;

import java.util.Collections;
import java.util.List;

/**
 * @author gregorry
 */
@Service
public class DirectProjectLinkResolver implements ProjectLinkResolver {

    private final ProjectService projectService;

    public DirectProjectLinkResolver(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public List<Project> resolveProjectLink(final ProjectLink projectLink) {
        final DirectProjectLink directProjectLink = DirectProjectLink.of(projectLink);
        final ProjectEntity project = projectService.getProjects(projectLink.getServerId())
                .stream()
                .filter(p -> p.getKey().equals(directProjectLink.getLink()))
                .findFirst()
                .orElseThrow(() -> new SQCompanionException("Can't find project by direct project link with key: " + directProjectLink.getLink()));
        return Collections.singletonList(
                Project.builder()
                        .key(project.getKey())
                        .name(project.getName())
                        .serverId(projectLink.getServerId())
                        .build()
        );
    }

}
