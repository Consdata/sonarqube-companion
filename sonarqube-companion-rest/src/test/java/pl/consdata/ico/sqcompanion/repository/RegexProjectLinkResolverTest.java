package pl.consdata.ico.sqcompanion.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;
import pl.consdata.ico.sqcompanion.config.RegexProjectLink;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.project.ProjectEntity;
import pl.consdata.ico.sqcompanion.project.ProjectService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private ProjectService projectService;
    private List<ProjectEntity> sonarQubeProjects;

    @Before
    public void setup() {
        appConfig = AppConfig
                .builder()
                .server(
                        ServerDefinition
                                .builder()
                                .id(ANY_SERVER_ID)
                                .build()
                ).build();

        projectService = mock(ProjectService.class);
        final ProjectLinkResolverFactory projectLinkResolverFactory = mock(ProjectLinkResolverFactory.class);
        when(
                projectLinkResolverFactory.getResolver(Mockito.eq(ProjectLinkType.REGEX))
        ).thenReturn(
                new RegexProjectLinkResolver(projectService)
        );

        sonarQubeProjects = new ArrayList<>();
        when(
                projectService.getProjects(anyString())
        ).thenReturn(
                sonarQubeProjects
        );

        service = new RepositoryService(appConfig, projectLinkResolverFactory);
    }

    @Test
    public void shouldIncludeProjects() {
        final String expectedProjectKey = "pl.consdata.ico.sqcompanion:expected-project:master";
        addSonarQubeProjects(expectedProjectKey);

        // given
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

        // when
        service.syncGroups();
        final Group rootGroup = service.getRootGroup();

        // then
        assertThat(rootGroup.getProjects()).isNotEmpty();
        assertThat(rootGroup.getProjects()).anySatisfy(project -> project.getKey().equals(expectedProjectKey));
    }

    @Test
    public void shouldExcludeProjects() {
        addSonarQubeProjects("project:a");

        // given
        appConfig.setRootGroup(
                GroupDefinition
                        .builder()
                        .projectLink(
                                ProjectLink
                                        .builder()
                                        .config(RegexProjectLink.INCLUDE, Arrays.asList("project.*"))
                                        .config(RegexProjectLink.EXCLUDE, Arrays.asList(".*a"))
                                        .type(ProjectLinkType.REGEX)
                                        .serverId(ANY_SERVER_ID)
                                        .build()
                        )
                        .build()
        );

        // when
        service.syncGroups();
        final Group rootGroup = service.getRootGroup();

        // then
        assertThat(rootGroup.getProjects()).isEmpty();
    }

    private void addSonarQubeProjects(final String... projectKeys) {
        sonarQubeProjects.addAll(
                Arrays.asList(projectKeys)
                .stream()
                .map(
                        projectKey -> ProjectEntity.builder().key(projectKey).build()
                )
                .collect(Collectors.toList())
        );
    }

}
