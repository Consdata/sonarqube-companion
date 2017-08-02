package pl.consdata.ico.sqcompanion.history;

import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncProjectViolationsHistoryTest extends BaseItTest {

    @Test
    public void shouldSyncProjectViolationHistory() throws SynchronizationException {
        // given
        inMemorySonarQubeFacade.getInMemoryRepository()
                .getProjects()
                .get(Project.getProjectUniqueId(TestAppConfig.Servers.Server1.ID, TestAppConfig.RootGroup.Project1.KEY))
                .getMeasures()
                .add(
                        SonarQubeMeasure
                                .builder()
                                .blockers(1)
                                .date(new Date())
                                .build()
                );

        // when
        projectHistoryService.syncProjectsHistory();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = projectHistoryService.getProjectViolationsHistory(
                Project
                        .builder()
                        .serverId(TestAppConfig.Servers.Server1.ID)
                        .key(TestAppConfig.RootGroup.Project1.KEY)
                        .build(),
                Optional.empty()
        );
        assertThat(projectViolations).isEmpty();
    }

}
