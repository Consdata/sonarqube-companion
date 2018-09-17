package pl.consdata.ico.sqcompanion;

import lombok.experimental.UtilityClass;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.DirectProjectLink;
import pl.consdata.ico.sqcompanion.config.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.ProjectLink;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;

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
												.config(DirectProjectLink.LINK, TestAppConfig.RootGroup.Project1.KEY)
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

			public static final String KEY =  Servers.Server1.ID + "$pl.proj:project1";

			public static final String NAME = "Project1";

		}

	}

	@UtilityClass
	public static class Users {

		public static final String USER_1 = "user@example.com";

	}

}
