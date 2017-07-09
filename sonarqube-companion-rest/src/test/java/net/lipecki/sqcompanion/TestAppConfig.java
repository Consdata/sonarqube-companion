package net.lipecki.sqcompanion;

import lombok.experimental.UtilityClass;
import net.lipecki.sqcompanion.config.*;

/**
 * @author gregorry
 */
@UtilityClass
public class TestAppConfig {

	public static AppConfig config() {
		return AppConfig
				.builder()
				.server(
						ServerDefinition.builder()
								.id(Servers.Server1.ID)
								.url(Servers.Server1.URL)
								.build()
				)
				.rootGroup(
						GroupDefinition.builder()
								.projectLink(
										ProjectLink.builder()
												.serverId(Servers.Server1.ID)
												.link(TestAppConfig.RootGroup.Project1.KEY)
												.type(ProjectLinkType.DIRECT)
												.build()
								)
								.build()
				)
				.build();
	}

	@UtilityClass
	public static class Servers {

		@UtilityClass
		public static class Server1 {

			public static final String ID = "Server1";

			public static final String URL = "http://server1.sonarqube/";

		}

	}

	@UtilityClass
	public static class RootGroup {

		@UtilityClass
		public static class Project1 {

			public static final String KEY = "pl.proj.";

			public static final String NAME = "Project1";

		}

	}

}
