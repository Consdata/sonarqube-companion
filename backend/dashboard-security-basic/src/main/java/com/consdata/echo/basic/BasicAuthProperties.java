package com.consdata.echo.basic;

import com.consdata.echo.api.User;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "echo.auth.basic")
public record BasicAuthProperties(
        List<User> users,
        boolean enabled
) {
}
