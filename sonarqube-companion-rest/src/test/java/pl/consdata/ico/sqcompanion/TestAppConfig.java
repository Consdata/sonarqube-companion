package pl.consdata.ico.sqcompanion;

import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;

/**
 * @author gregorry
 */
public class TestAppConfig {

	public static AppConfig config() {
		return AppConfig
				.builder()
				.rootGroup(
						GroupDefinition.builder()
								.projectLink(
										ProjectLink.builder()
												.link(TestAppConfig.RootGroup.Project1.KEY)
												.type(ProjectLinkType.DIRECT)
												.build()
								)
								.build()
				)
				.build();
	}

	public static class RootGroup {

		public static class Project1 {

			public static final String KEY = "Project1";

		}

	}

}
