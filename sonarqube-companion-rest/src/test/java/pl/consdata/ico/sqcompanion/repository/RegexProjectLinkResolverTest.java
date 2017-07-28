package pl.consdata.ico.sqcompanion.repository;

import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;
import pl.consdata.ico.sqcompanion.config.RegexProjectLink;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

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
    private SonarQubeFacade sonarQubeFacade;

    @Before
    public void setup() {
        appConfig = AppConfig.builder().build();
        sonarQubeFacade = mock(SonarQubeFacade.class);
        final ProjectLinkResolverFactory projectLinkResolverFactory = mock(ProjectLinkResolverFactory.class);
        when(projectLinkResolverFactory.getResolver(Mockito.eq(ProjectLinkType.REGEX)))
                .thenReturn(new RegexProjectLinkResolver(sonarQubeFacade));
        service = new RepositoryService(appConfig, projectLinkResolverFactory);
    }

    @Test
    public void shouldMatchRegexProjectLink() {
        final String expectedProjectKey = "pl.consdata.ico.sqcompanion:expected-project:master";

        // given
        appConfig.setRootGroup(
                GroupDefinition
                        .builder()
                        .projectLink(
                                ProjectLink
                                        .builder()
                                        .config(RegexProjectLink.INCLUDE, Arrays.asList("pl.consdata.ico.sqcompanion.*"))
                                        .type(ProjectLinkType.REGEX)
                                        .serverId("any-server-id")
                                        .build()
                        )
                        .build()
        );
        when(sonarQubeFacade.getProjects(anyString())).thenReturn(
                Arrays.asList(SonarQubeProject.builder().key(expectedProjectKey).build())
        );

        // when
        service.syncGroups();
        final Group rootGroup = service.getRootGroup();

        // then
        assertThat(rootGroup.getProjects()).isNotEmpty();
        assertThat(rootGroup.getProjects()).anySatisfy(project -> project.getKey().equals(expectedProjectKey));
    }

}
