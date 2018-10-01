package pl.consdata.ico.sqcompanion.history;

import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserProjectSummaryViolationHistoryEntry;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncUserViolationsSummaryHistoryTest extends BaseItTest {

    public static final String USER = TestAppConfig.Users.USER_1;
    public static final String SERVER_ID = TestAppConfig.Servers.Server1.ID;
    public static final String PROJECT_KEY = TestAppConfig.RootGroup.Project1.KEY;
    public static final LocalDate NOW = LocalDate.now();

    @Test
    public void shouldEstimateHistoryForDatesWithoutAnalyses() throws SynchronizationException {
        // given
        addUserIssue(USER, PROJECT_KEY, NOW.minusDays(3), SonarQubeIssueSeverity.BLOCKER);
        addUserIssue(USER, PROJECT_KEY, NOW.minusDays(3), SonarQubeIssueSeverity.BLOCKER);
        addUserIssue(USER, PROJECT_KEY, NOW.minusDays(3), SonarQubeIssueSeverity.BLOCKER);
        addStoredAnalysys(NOW.minusDays(3), 3);
        addUserIssue(USER, PROJECT_KEY, NOW.minusDays(1), SonarQubeIssueSeverity.BLOCKER);

        // when
        tickSynchronization();

        // then
        final List<UserProjectSummaryViolationHistoryEntry> userIssues = userViolationHistoryRepository.findByUserId(USER);
        assertThat(userIssues)
                .extracting(UserProjectSummaryViolationHistoryEntry::getBlockers)
                .endsWith(3, 3, 4);
    }

    private void addStoredAnalysys(LocalDate threeDaysAgo, int blockers) {
        userViolationHistoryRepository.save(
                UserProjectSummaryViolationHistoryEntry.builder()
                        .id(UserProjectSummaryViolationHistoryEntry.combineId(
                                SERVER_ID,
                                USER,
                                PROJECT_KEY,
                                threeDaysAgo
                        ))
                        .serverId(SERVER_ID)
                        .projectKey(PROJECT_KEY)
                        .userId(USER)
                        .date(threeDaysAgo)
                        .blockers(blockers)
                        .build()
        );
    }

}
