package pl.consdata.ico.sqcompanion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.history.SyncUserProjectViolationsDiffHistoryTest;
import pl.consdata.ico.sqcompanion.hook.WebhookScheduler;
import pl.consdata.ico.sqcompanion.members.MemberRepository;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.members.MembershipRepository;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.InMemorySonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.sync.SynchronizationService;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryService;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserViolationDiffRepository;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationHistoryRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseItTest.ItTestConfiguration.class}, properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("ittest")
@TestPropertySource("/it-test.properties")
@Transactional
public abstract class BaseItTest {

    @Autowired
    protected WebhookScheduler webhookScheduler;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected SynchronizationService synchronizationService;

    @Autowired
    protected ProjectViolationsHistoryService projectViolationsHistoryService;

    @Autowired
    protected InMemorySonarQubeFacade inMemorySonarQubeFacade;

    @Autowired
    protected ProjectHistoryRepository projectHistoryRepository;

    @Autowired
    protected UserViolationDiffRepository userViolationDiffRepository;

    @Autowired
    protected UserViolationHistoryRepository userViolationHistoryRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MembershipRepository membershipRepository;

    @Autowired
    protected MemberService memberService;

    @Before
    public void setUpBaseItTest() {
        inMemorySonarQubeFacade.resetFacade();
    }

    @Test
    public void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    protected void tickSynchronization() throws SynchronizationException {
        synchronizationService.acquireAndStartSynchronization();
    }

    protected void addUserIssue(String user, String project, LocalDate date, SonarQubeIssueSeverity severity) {
        inMemorySonarQubeFacade.getInMemoryRepository()
                .getProjects()
                .get(Project.getProjectUniqueId(SyncUserProjectViolationsDiffHistoryTest.SERVER_ID, project))
                .getIssues()
                .add(
                        SonarQubeIssue.builder()
                                .author(user)
                                .creationDate(LocalDateUtil.asDate(date))
                                .severity(severity)
                                .build()
                );
    }

    @TestConfiguration
    static class ItTestConfiguration {

        @Bean
        public AppConfig appConfig() {
            return TestAppConfig.config();
        }

        @Bean
        public SonarQubeFacade sonarQubeFacade(final InMemorySonarQubeFacade inMemorySonarQubeFacade) {
            return inMemorySonarQubeFacade;
        }

        @Bean
        public InMemorySonarQubeFacade inMemorySonarQubeFacade() {
            return new InMemorySonarQubeFacade();
        }

    }

}
