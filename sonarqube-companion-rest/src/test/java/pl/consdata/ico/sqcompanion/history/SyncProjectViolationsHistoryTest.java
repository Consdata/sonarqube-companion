package pl.consdata.ico.sqcompanion.history;

import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncProjectViolationsHistoryTest extends BaseItTest {

    @Test
    public void shuldNotSyncWhenNoHistoryAndNoAnalyses() throws SynchronizationException {
        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = violationsHistoryService.getProjectViolationsHistory(
                Project
                        .builder()
                        .serverId(TestAppConfig.Servers.Server1.ID)
                        .key(TestAppConfig.RootGroup.Project1.KEY)
                        .build(),
                Optional.empty()
        );
        assertThat(projectViolations).isEmpty();
    }

    @Test
    public void shouldSyncProjectViolationHistory() throws SynchronizationException {
        // given
        int latestAnalyseBlockers = 2;
        final List<SonarQubeMeasure> projectMeasures = inMemorySonarQubeFacade.getInMemoryRepository()
                .getProjects()
                .get(Project.getProjectUniqueId(TestAppConfig.Servers.Server1.ID, TestAppConfig.RootGroup.Project1.KEY))
                .getMeasures();
        projectMeasures.add(
                SonarQubeMeasure
                        .builder()
                        .blockers(1)
                        .date(LocalDateUtil.asDate(LocalDate.now().minusDays(2)))
                        .build()
        );
        projectMeasures.add(
                SonarQubeMeasure
                        .builder()
                        .blockers(latestAnalyseBlockers)
                        .date(LocalDateUtil.asDate(LocalDate.now().minusDays(1)))
                        .build()
        );

        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = violationsHistoryService.getProjectViolationsHistory(
                Project
                        .builder()
                        .serverId(TestAppConfig.Servers.Server1.ID)
                        .key(TestAppConfig.RootGroup.Project1.KEY)
                        .build(),
                Optional.empty()
        );
        assertThat(projectViolations).hasSize(3);
        assertThat(projectViolations.get(2).getBlockers()).isEqualTo(latestAnalyseBlockers);
    }

    @Test
    public void shouldEstimateWhenHistoryExistButNoAnalyses() throws SynchronizationException {
        // when
        int latestAvailableHistoryBlockers = 2;
        projectHistoryRepository.save(
                ProjectHistoryEntryEntity
                        .builder()
                        .id(
                                ProjectHistoryEntryEntity.combineId(
                                        TestAppConfig.Servers.Server1.ID,
                                        TestAppConfig.RootGroup.Project1.KEY,
                                        LocalDate.now().minusDays(2))
                        )
                        .projectKey(TestAppConfig.RootGroup.Project1.KEY)
                        .serverId(TestAppConfig.Servers.Server1.ID)
                        .blockers(latestAvailableHistoryBlockers)
                        .date(LocalDate.now().minusDays(2))
                        .build()
        );

        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = violationsHistoryService.getProjectViolationsHistory(
                Project
                        .builder()
                        .serverId(TestAppConfig.Servers.Server1.ID)
                        .key(TestAppConfig.RootGroup.Project1.KEY)
                        .build(),
                Optional.empty()
        );
        assertThat(projectViolations).hasSize(3);
        assertThat(projectViolations.get(2).getBlockers()).isEqualTo(latestAvailableHistoryBlockers);
    }

}
