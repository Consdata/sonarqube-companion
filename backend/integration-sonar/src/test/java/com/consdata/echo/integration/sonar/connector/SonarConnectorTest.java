package com.consdata.echo.integration.sonar.connector;

import com.consdata.echo.integration.sonar.SonarIntegrationAutoConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

class SonarConnectorTest {

    @Test
    public void test() {
        LocalDateTime now = LocalDateTime.now();

        Assertions.assertThat(
                now.format(new DateTimeFormatterBuilder()
                                .appendPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                        .toFormatter())).isBlank();
    }

}
