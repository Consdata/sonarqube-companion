package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLinkType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetGroupsTest {

    private AppConfig appConfig;
    private RepositoryService service;

    @Before
    public void setup() {
        appConfig = AppConfig.builder().build();

        final ProjectLinkResolverFactory projectLinkResolverFactory = mock(ProjectLinkResolverFactory.class);
        final ProjectLinkResolver linkResolver = mock(ProjectLinkResolver.class);
        when(projectLinkResolverFactory.getResolver(Mockito.eq(ProjectLinkType.DIRECT))).thenReturn(linkResolver);

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

}
