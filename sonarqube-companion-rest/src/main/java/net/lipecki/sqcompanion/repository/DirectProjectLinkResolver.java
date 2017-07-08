package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.SQCompanionException;
import net.lipecki.sqcompanion.config.ProjectLink;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import net.lipecki.sqcompanion.sonarqube.SonarQubeProject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author gregorry
 */
@Service
public class DirectProjectLinkResolver implements ProjectLinkResolver {

	private final SonarQubeFacade sonarQubeFacade;

	public DirectProjectLinkResolver(final SonarQubeFacade sonarQubeFacade) {
		this.sonarQubeFacade = sonarQubeFacade;
	}

	@Override
	public List<Project> resolveProjectLink(final ProjectLink projectLink) {
		final SonarQubeProject project = sonarQubeFacade.getProjects(projectLink.getServerId())
				.stream()
				.filter(p -> p.getKey().equals(projectLink.getLink()))
				.findFirst()
				.orElseThrow(() -> new SQCompanionException("Can't find project by direct project link with key: " + projectLink.getLink()));
		return Collections.singletonList(
				Project.builder()
						.key(project.getKey())
						.name(project.getName())
						.serverId(projectLink.getServerId())
						.build()
		);
	}

}
