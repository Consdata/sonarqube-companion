package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLink;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import net.lipecki.sqcompanion.sonarqube.SonarQubeProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import net.lipecki.sqcompanion.config.ProjectLinkType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author gregorry
 */
public class RegexProjectLinkResolverTest {

	private AppConfig appConfig;
	private RepositoryService service;
	private ProjectLinkResolverFactory projectLinkResolverFactory;
	private SonarQubeFacade sonarQubeFacade;
	private List<SonarQubeProject> mockedSonarQubeProjects;

	@Before
	public void setup() {
		appConfig = AppConfig.builder().build();

		mockedSonarQubeProjects = new ArrayList<>();
		sonarQubeFacade = mock(SonarQubeFacade.class);
		when(sonarQubeFacade.getProjects(anyString())).thenReturn(mockedSonarQubeProjects);

		projectLinkResolverFactory = mock(ProjectLinkResolverFactory.class);
		when(
				projectLinkResolverFactory.getResolver(Mockito.eq(ProjectLinkType.REGEX))
		).thenReturn(
				new RegexProjectLinkResolver(sonarQubeFacade)
		);

		service = new RepositoryService(appConfig, projectLinkResolverFactory);
	}

	@Test
	public void shouldMatchRegexProjectLink() {
		final String expectedProjectKey = "net.lipecki.sqcompanion:expected-project:master";

		// given
		appConfig.setRootGroup(
				GroupDefinition
						.builder()
						.projectLink(
								ProjectLink
										.builder()
										.link("net\\.lipecki\\.sqcompanion.*")
										.type(ProjectLinkType.REGEX)
										.serverId("any-server-id")
										.build()
						)
						.build()
		);
		mockedSonarQubeProjects.add(SonarQubeProject.builder().key(expectedProjectKey).build());

		// when
		service.syncGroups();
		final Group rootGroup = service.getRootGroup();

		// then
		assertThat(rootGroup.getProjects()).isNotEmpty();
		assertThat(rootGroup.getProjects()).anySatisfy(project -> project.getKey().equals(expectedProjectKey));
	}

}
