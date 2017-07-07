package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLink;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import net.lipecki.sqcompanion.config.ProjectLinkType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetGroupsTest {

    private AppConfig appConfig;
    private RepositoryService service;
    private ProjectLinkResolverFactory projectLinkResolverFactory;

    @Before
    public void setup() {
        appConfig = AppConfig.builder().build();

        projectLinkResolverFactory = mock(ProjectLinkResolverFactory.class);
        when(
                projectLinkResolverFactory.getResolver(Mockito.eq(ProjectLinkType.DIRECT))
        ).thenReturn(
                new DirectProjectLinkResolver()
        );

        service = new RepositoryService(appConfig, projectLinkResolverFactory);
    }

    @Test
    public void shouldGetRootGroupSummary() {
        final String name = "Sample Corp";
        final String uuid = UUID.randomUUID().toString();

        // given
        appConfig.setRootGroup(GroupDefinition.builder().uuid(uuid).name(name).build());

        // when
        service.syncGroups();
        final Group result = service.getRootGroup();

        // then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getUuid()).isEqualTo(uuid);
    }

    @Test
    public void shouldGetRootGroupProject() {
        final String projectLink = "sample-project-direct-link";

        // given
        appConfig.setRootGroup(
                GroupDefinition
                        .builder()
                        .projectLink(
                                ProjectLink
                                        .builder()
                                        .link(projectLink)
                                        .type(ProjectLinkType.DIRECT)
                                        .build()
                        )
                        .build()
        );

        // when
        service.syncGroups();
        final Group result = service.getRootGroup();

        // then
        assertThat(result.getProjects()).isNotEmpty();
        final Project project = result.getProjects().get(0);
        assertThat(project.getKey()).isEqualTo(projectLink);
    }

}
