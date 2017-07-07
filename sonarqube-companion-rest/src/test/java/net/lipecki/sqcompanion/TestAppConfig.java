package net.lipecki.sqcompanion;

import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLink;
import net.lipecki.sqcompanion.config.ProjectLinkType;

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
