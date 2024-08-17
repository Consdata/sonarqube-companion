package com.consdata.echo.integration.sonar.connector;

import com.consdata.echo.integration.sonar.SonarIntegrationAutoConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SonarIntegrationAutoConfiguration.class)
class SonarConnectorTest {
    @Autowired
    SonarConnector connector;

    @Test
    public void test() {
        SqSearchIssuesResponse r = connector.search("", 0, 0);

        Assertions.assertThat(r).isNull();
    }

}
