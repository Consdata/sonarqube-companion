package pl.consdata.ico.sqcompanion;

import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.InMemorySonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sync.SynchronizationException;
import pl.consdata.ico.sqcompanion.sync.SynchronizationService;
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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseItTest.ItTestConfiguration.class})
@ActiveProfiles("ittest")
@TestPropertySource("/it-test.properties")
public abstract class BaseItTest {

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

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	protected SynchronizationService synchronizationService;

	@Test
	public void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	protected void tickSynchronization() throws SynchronizationException {
		synchronizationService.runSynchronization();
	}

}
