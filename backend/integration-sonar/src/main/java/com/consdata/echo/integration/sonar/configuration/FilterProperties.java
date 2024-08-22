package com.consdata.echo.integration.sonar.configuration;

import java.util.List;

public record FilterProperties(
        List<String> users,
        List<String> projects
) {
}
