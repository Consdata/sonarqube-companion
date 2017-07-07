package net.lipecki.sqcompanion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.repository.RepositoryService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseItTest.ItTestConfiguration.class})
public abstract class BaseItTest {

	@TestConfiguration
	static class ItTestConfiguration {

		@Bean
		public AppConfig appConfig() {
			return TestAppConfig.config();
		}

	}

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected ApplicationContext applicationContext;

	@Test
	public void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

}
