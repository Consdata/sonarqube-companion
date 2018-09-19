package pl.consdata.ico.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;
import pl.consdata.ico.sqcompanion.violation.user.UserProjectHistoryEntryEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class SyncUserProjectViolationsHistoryTest extends BaseItTest {

    public static final String USER = TestAppConfig.Users.USER_1;
    public static final String SERVER_ID = TestAppConfig.Servers.Server1.ID;
    public static final String PROJECT_KEY = TestAppConfig.RootGroup.Project1.KEY;
    public static final LocalDate NOW = LocalDate.now();

    @Test
    public void shouldSyncUserViolations() throws SynchronizationException {
        // given
        addUserIssue(USER, PROJECT_KEY, NOW.minus(2, ChronoUnit.DAYS), SonarQubeIssueSeverity.BLOCKER);
        addUserIssue(USER, PROJECT_KEY, NOW.minus(2, ChronoUnit.DAYS), SonarQubeIssueSeverity.BLOCKER);
        addUserIssue(USER, PROJECT_KEY, NOW, SonarQubeIssueSeverity.BLOCKER);

        // when
        tickSynchronization();

        // then
        final List<UserProjectHistoryEntryEntity> userHistory = userProjectHistoryRepository.findByUserId(USER);
        assertThat(userHistory)
                .extracting(UserProjectHistoryEntryEntity::getBlockers)
                .containsExactly(2);
    }

    @Test
    public void shouldOverrideTodayEntries() throws SynchronizationException {
        // given
        userProjectHistoryRepository.save(
                UserProjectHistoryEntryEntity.builder()
                        .id(UserProjectHistoryEntryEntity.combineId(SERVER_ID, USER, PROJECT_KEY, NOW))
                        .serverId(SERVER_ID)
                        .projectKey(PROJECT_KEY)
                        .userId(USER)
                        .blockers(0)
                        .date(NOW)
                        .build()
        );
        addUserIssue(USER, PROJECT_KEY, NOW, SonarQubeIssueSeverity.BLOCKER);

        // when
        tickSynchronization();

        // then
        final List<UserProjectHistoryEntryEntity> userHistory = userProjectHistoryRepository.findByUserId(USER);
        assertThat(userHistory)
                .extracting(UserProjectHistoryEntryEntity::getBlockers)
                .containsExactly(1);
    }

    private void addUserIssue(String user, String project, LocalDate date, SonarQubeIssueSeverity severity) {
        inMemorySonarQubeFacade.getInMemoryRepository()
                .getProjects()
                .get(Project.getProjectUniqueId(SERVER_ID, project))
                .getIssues()
                .add(
                        SonarQubeIssue.builder()
                                .author(user)
                                .creationDate(LocalDateUtil.asDate(date))
                                .severity(severity)
                                .build()
                );
    }

}
