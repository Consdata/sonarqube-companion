package com.consdata.echo.integration.sonar.configuration;

public class UndefinedServerException extends RuntimeException {
    public UndefinedServerException(String id) {
        super("Server with id " + id + " is not defined.");
    }
}
