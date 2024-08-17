package com.consdata.echo.integration.sonar.configuration;

import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isAllBlank;

public record SonarAuthorizationProperties(
        String token,
        String username,
        String password
) {

    public String asBasic() {
        if (isAllBlank(username, password)) {
            return asBasic(token, StringUtils.EMPTY);
        } else {
            return asBasic(username, password);
        }
    }

    private String asBasic(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }
}
