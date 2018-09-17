package pl.consdata.ico.sqcompanion.history;

import org.junit.Test;
import pl.consdata.ico.sqcompanion.BaseItTest;
import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncUserProjectViolationsHistoryTest extends BaseItTest {

    public static final String USER = TestAppConfig.Users.USER_1;
    public static final String SERVER_ID = TestAppConfig.Servers.Server1.ID;
    public static final String PROJECT_KEY = TestAppConfig.RootGroup.Project1.KEY;

    @Test
    public void shouldSyncUserViolations() throws SynchronizationException {
        // given
        addUserIssue(USER, PROJECT_KEY, LocalDate.now(), SonarQubeIssueSeverity.BLOCKER);

        // when
        tickSynchronization();

        // then
        assertThat(userProjectHistoryRepository.findByUserId(USER)).hasSize(1);
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
