package pl.consdata.ico.sqcompanion.repository;

import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
public class RegexProjectLinkResolver implements ProjectLinkResolver {

	private final SonarQubeFacade sonarQubeFacade;

	public RegexProjectLinkResolver(final SonarQubeFacade sonarQubeFacade) {
		this.sonarQubeFacade = sonarQubeFacade;
	}

	@Override
	public List<Project> resolveProjectLink(final ProjectLink projectLink) {
		return sonarQubeFacade.getProjects()
				.stream()
				.filter(project -> project.getKey().matches(projectLink.getLink()))
				.map(
						project -> Project.builder()
								.key(project.getKey())
								.build()
				)
				.collect(Collectors.toList());
	}

}
