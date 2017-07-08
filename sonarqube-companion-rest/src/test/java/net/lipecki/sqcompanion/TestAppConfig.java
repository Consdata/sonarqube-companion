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
                .rootGroup(
                        GroupDefinition.builder()
//                                .projectLink(
//                                        ProjectLink.builder()
//                                                .link(TestAppConfig.RootGroup.Project1.KEY)
//                                                .type(ProjectLinkType.DIRECT)
//                                                .build()
//                                )
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
