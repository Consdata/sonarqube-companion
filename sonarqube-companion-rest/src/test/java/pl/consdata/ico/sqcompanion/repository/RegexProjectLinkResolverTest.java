package pl.consdata.ico.sqcompanion.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeProject;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
		when(sonarQubeFacade.getProjects()).thenReturn(mockedSonarQubeProjects);

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
		final String expectedProjectKey = "pl.consdata.sqcompanion:expected-project:master";

		// given
		appConfig.setRootGroup(
				GroupDefinition
						.builder()
						.projectLink(
								ProjectLink
										.builder()
										.link("pl.consdata.sqcompanion.*")
										.type(ProjectLinkType.REGEX)
										.build()
						)
						.build()
		);
		mockedSonarQubeProjects.add(SonarQubeProject.builder().key(expectedProjectKey).build());

		// when
		final Group rootGroup = service.getRootGroup();

		// then
		assertThat(rootGroup.getProjects()).isNotEmpty();
		assertThat(rootGroup.getProjects()).anySatisfy(project -> project.getKey().equals(expectedProjectKey));
	}

}
