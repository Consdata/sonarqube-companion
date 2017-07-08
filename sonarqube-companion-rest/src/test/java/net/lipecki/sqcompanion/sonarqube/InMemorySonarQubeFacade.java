package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.TestAppConfig;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
public class InMemorySonarQubeFacade implements SonarQubeFacade {

	private final InMemoryRepository inMemoryRepository;

	public InMemorySonarQubeFacade() {
		inMemoryRepository = InMemoryRepository
				.builder()
				.project(
						TestAppConfig.RootGroup.Project1.KEY,
						InMemoryProject
								.builder()
								.project(
										SonarQubeProject
												.builder()
												.key(TestAppConfig.RootGroup.Project1.KEY)
												.name(TestAppConfig.RootGroup.Project1.NAME)
												.build()
								)
								.build()
				)
				.build();
	}

	@Override
	public List<SonarQubeProject> getProjects(final String serverId) {
		return inMemoryRepository
				.getProjects()
				.values()
				.stream()
				.map(InMemoryProject::getProject)
				.collect(Collectors.toList());
	}

	@Override
	public List<SonarQubeIssue> getIssues(final String serverId, final String projectKey) {
		return inMemoryRepository
				.getProjects()
				.get(projectKey)
				.getIssues();
	}

	@Override
	public List<SonarQubeMeasure> getProjectMeasureHistory(final String serverId, final String projectKey, final Date fromDate) {
		return inMemoryRepository
				.getProjects()
				.get(projectKey)
				.getMeasures()
				.stream()
				.filter(m -> fromDate == null || m.getDate().after(fromDate))
				.collect(Collectors.toList());
	}

}
