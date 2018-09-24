package pl.consdata.ico.sqcompanion.history;

import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

import java.time.LocalDate;
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
        final ViolationsHistory projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).isNotNull();
        assertThat(projectViolations.getViolationHistoryEntries()).isEmpty();
    }

    @Test
    public void shouldSyncProjectViolationHistory() throws SynchronizationException {
        // given
        int latestAnalyseBlockers = 2;
        addAnalysedMeasure(LocalDate.now().minusDays(3), 1);
        addAnalysedMeasure(LocalDate.now().minusDays(2), latestAnalyseBlockers);

        // when
        tickSynchronization();

        // then
        final ViolationsHistory projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).isNotNull();
        assertThat(projectViolations.getViolationHistoryEntries()).hasSize(3);
        assertThat(projectViolations.getViolationHistoryEntries().get(2).getViolations().getBlockers()).isEqualTo(latestAnalyseBlockers);
    }

    /**
     * Fixes: https://github.com/Consdata/sonarqube-companion/issues/34
     */
    @Test
    public void shouldEstimateWhenHistoryExistButNoAnalyses() throws SynchronizationException {
        // when
        int latestAvailableHistoryBlockers = 2;
        addHistoricEntry(LocalDate.now().minusDays(3), latestAvailableHistoryBlockers);

        // when
        tickSynchronization();

        // then
        final ViolationsHistory projectViolations = getProjectViolationsHistory();
        assertThat(projectViolations).isNotNull();
        assertThat(projectViolations.getViolationHistoryEntries()).hasSize(3);
        assertThat(projectViolations.getViolationHistoryEntries().get(2).getViolations().getBlockers()).isEqualTo(latestAvailableHistoryBlockers);
    }

    private ViolationsHistory getProjectViolationsHistory() {
        return projectViolationsHistoryService.getProjectViolationsHistory(
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
