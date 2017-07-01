package pl.consdata.ico.sqcompanion.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.group.Group;
import pl.consdata.ico.sqcompanion.group.GroupDefinition;
import pl.consdata.ico.sqcompanion.project.Project;
import pl.consdata.ico.sqcompanion.project.ProjectLink;
import pl.consdata.ico.sqcompanion.project.ProjectLinkType;

import java.util.UUID;

public class GetGroupSummaryTest {

    private AppConfig appConfig;
    private RepositoryService service;

    @Before
    public void setup() {
        appConfig = AppConfig.builder().build();
        service = new RepositoryService(appConfig);
    }

    @Test
    public void shouldGetRootGroupSummary() {
        final String name = "Sample Corp";
        final String uuid = UUID.randomUUID().toString();

        // given
        appConfig.setRootGroup(GroupDefinition.builder().uuid(uuid).name(name).build());

        // when
        final Group result = service.getGroupSummary();

        // then
        Assertions.assertThat(result.getName()).isEqualTo(name);
        Assertions.assertThat(result.getUuid()).isEqualTo(uuid);
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
        final Group result = service.getGroupSummary();

        // then
        Assertions.assertThat(result.getProjects()).isNotEmpty();
        final Project project = result.getProjects().get(0);
    }

}
