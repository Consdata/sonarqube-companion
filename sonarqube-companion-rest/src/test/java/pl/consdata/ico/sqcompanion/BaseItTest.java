package pl.consdata.ico.sqcompanion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseItTest.TestAppConfigConfiguration.class})
public abstract class BaseItTest {

	// TODO: move to static class representing companion test config
	public static final String PROJECT_001_KEY = "project001";

	@TestConfiguration
	static class TestAppConfigConfiguration {

		@Bean
		public AppConfig appConfig() {
			return AppConfig.builder()
					.rootGroup(
							GroupDefinition.builder()
									.projectLink(
											ProjectLink.builder()
													.link(PROJECT_001_KEY)
													.type(ProjectLinkType.DIRECT)
													.build()
									)
									.build()
					)
					.build();
		}

	}

	@Autowired
	protected RepositoryService repositoryService;

	@Test
	public void contextLoads() {
	}

}
