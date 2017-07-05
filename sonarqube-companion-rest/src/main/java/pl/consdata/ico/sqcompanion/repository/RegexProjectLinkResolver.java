package pl.consdata.ico.sqcompanion.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class RegexProjectLinkResolver implements ProjectLinkResolver {

	private final SonarQubeFacade sonarQubeFacade;

	public RegexProjectLinkResolver(final SonarQubeFacade sonarQubeFacade) {
		this.sonarQubeFacade = sonarQubeFacade;
	}

	@Override
	public List<Project> resolveProjectLink(final ProjectLink projectLink) {
		return sonarQubeFacade.getProjects(projectLink.getServerId())
				.stream()
				.filter(project -> project.getKey().matches(projectLink.getLink()))
				.map(
						project -> Project.builder()
								.key(project.getKey())
								.name(project.getName())
								.serverId(projectLink.getServerId())
								.build()
				)
				.collect(Collectors.toList());
	}

}
