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

    public static final String SERVER_ID = TestAppConfig.Servers.Server1.ID;
    public static final String PROJECT_KEY = TestAppConfig.RootGroup.Project1.KEY;

    @Test
    public void shouldNotSyncWhenNoHistoryAndNoAnalyses() throws SynchronizationException {
        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).isEmpty();
    }

    @Test
    public void shouldSyncProjectViolationHistory() throws SynchronizationException {
        // given
        int latestAnalyseBlockers = 2;
        addAnalysedMeasure(LocalDate.now().minusDays(2), 1);
        addAnalysedMeasure(LocalDate.now().minusDays(1), latestAnalyseBlockers);

        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).hasSize(3);
        assertThat(projectViolations.get(2).getBlockers()).isEqualTo(latestAnalyseBlockers);
    }

    @Test
    public void shouldEstimateWhenHistoryExistButNoAnalyses() throws SynchronizationException {
        // when
        int latestAvailableHistoryBlockers = 2;
        addHistoricEntry(LocalDate.now().minusDays(2), latestAvailableHistoryBlockers);

        // when
        tickSynchronization();

        // then
        final List<ProjectHistoryEntryEntity> projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).hasSize(3);
        assertThat(projectViolations.get(2).getBlockers()).isEqualTo(latestAvailableHistoryBlockers);
    }

    private List<ProjectHistoryEntryEntity> getProjectViolationsHistory() {
        return violationsHistoryService.getProjectViolationsHistory(
                Project
                        .builder()
                        .serverId(SERVER_ID)
                        .key(PROJECT_KEY)
                        .build(),
                Optional.empty()
        );
    }

    private void addAnalysedMeasure(final LocalDate localDate, final int blockers) {
        inMemorySonarQubeFacade.getInMemoryRepository()
                .getProjects()
                .get(Project.getProjectUniqueId(SERVER_ID, PROJECT_KEY))
                .getMeasures()
                .add(
                        SonarQubeMeasure
                                .builder()
                                .blockers(blockers)
                                .date(LocalDateUtil.asDate(localDate))
                                .build()
                );
    }

    private void addHistoricEntry(LocalDate date, final int blockers) {
        projectHistoryRepository.save(
                ProjectHistoryEntryEntity
                        .builder()
                        .id(ProjectHistoryEntryEntity.combineId(SERVER_ID, PROJECT_KEY, date))
                        .serverId(SERVER_ID)
                        .projectKey(PROJECT_KEY)
                        .blockers(blockers)
                        .date(date)
                        .build()
        );
    }

}
