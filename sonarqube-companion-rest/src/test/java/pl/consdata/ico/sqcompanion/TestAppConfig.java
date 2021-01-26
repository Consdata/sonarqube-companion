package pl.consdata.ico.sqcompanion;

import lombok.experimental.UtilityClass;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.DirectProjectLink;
import pl.consdata.ico.sqcompanion.config.model.*;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;


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
                                .uuid(Servers.Server1.UUID)
                                .id(Servers.Server1.ID)
                                .url(Servers.Server1.URL)
                                .aliases(emptyList())
                                .build()
                )
                .rootGroup(
                        GroupDefinition.builder()
                                .uuid("group1")
                                .projectLink(
                                        ProjectLink.builder()
                                                .serverId(Servers.Server1.ID)
                                                .config(DirectProjectLink.LINK, RootGroup.Project1.KEY)
                                                .type(ProjectLinkType.DIRECT)
                                                .build()
                                )
                                .group(
                                        GroupDefinition.builder()
                                                .uuid("group2")
                                                .build()
                                )
                                .build()
                )
                .members(MembersDefinition.builder()
                        .local(Member.builder()
                                .aliases(Members.Member1.aliases)
                                .groups(Members.Member1.groups)
                                .firstName(Members.Member1.firstName)
                                .lastName(Members.Member1.lastName)
                                .uuid(Members.Member1.uuid)
                                .build())
                        .build())
                .build();
    }

    @UtilityClass
    public static class Servers {

        @UtilityClass
        public static class Server1 {

            public static final String ID = "Server1";
            public static final String UUID = "Server1";
            public static final String URL = "http://server1.sonarqube/";

        }

    }

    @UtilityClass
    public static class RootGroup {

        @UtilityClass
        public static class Project1 {

            public static final String KEY = Servers.Server1.ID + "$pl.proj:project1";

            public static final String NAME = "Project1";

        }

    }

    @UtilityClass
    public static class Users {

        public static final String USER_1 = "user@example.com";

    }

    @UtilityClass
    public static class Members {
        @UtilityClass
        public static class Member1 {
            public static final String uuid = "member1";
            public static final String firstName = "Member";
            public static final String lastName = "1";
            public static final Set<String> aliases = asSet("alias1", "alias2");
            public static final Set<String> groups = asSet("group1");
        }

    }

}
