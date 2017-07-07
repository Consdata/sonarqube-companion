package net.lipecki.sqcompanion.repository;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import org.springframework.stereotype.Service;
import net.lipecki.sqcompanion.config.ProjectLink;

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
