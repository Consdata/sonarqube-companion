package net.lipecki.sqcompanion;

import lombok.experimental.UtilityClass;
import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLink;
import net.lipecki.sqcompanion.config.ProjectLinkType;

/**
 * @author gregorry
 */
@UtilityClass
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

    @UtilityClass
    public static class RootGroup {

        @UtilityClass
        public static class Project1 {

            public static final String KEY = "Project1";

        }

    }

}
