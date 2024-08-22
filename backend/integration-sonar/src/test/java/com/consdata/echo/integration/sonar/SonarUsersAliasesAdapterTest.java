package com.consdata.echo.integration.sonar;

import com.consdata.echo.integration.sonar.configuration.SonarProperties;
import com.consdata.echo.organization.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SonarUsersAliasesAdapterTest {

    @Test
    public void shouldResolveUserAliases() {
        SonarProperties properties = new SonarProperties(null, null, Arrays.asList(
                "{0}@test.pl"
        ));
        SonarUsersAliasesAdapter uut = new SonarUsersAliasesAdapter(properties);

        Set<String> ids = uut.userIdsWithAliases(Arrays.asList(
                new User("foo", "test", "test"),
                new User("bar", "test", "test")
        ));

        assertThat(ids).contains("foo", "foo@test.pl", "bar", "bar@test.pl");
    }

}
