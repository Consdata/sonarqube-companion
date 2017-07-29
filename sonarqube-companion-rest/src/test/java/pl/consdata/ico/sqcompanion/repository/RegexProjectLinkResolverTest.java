package pl.consdata.ico.sqcompanion.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.consdata.ico.sqcompanion.config.*;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeProject;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author gregorry
 */
public class RegexProjectLinkResolverTest {

    public static final String ANY_SERVER_ID = "any-server-id";
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
        appConfig.setServers(
                Arrays.asList(
                        ServerDefinition
                                .builder()
                                .id(ANY_SERVER_ID)
                                .build()
                )
        );
        appConfig.setRootGroup(
                GroupDefinition
                        .builder()
                        .projectLink(
                                ProjectLink
                                        .builder()
                                        .config(RegexProjectLink.INCLUDE, Arrays.asList("pl.consdata.ico.sqcompanion.*"))
                                        .type(ProjectLinkType.REGEX)
                                        .serverId(ANY_SERVER_ID)
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
