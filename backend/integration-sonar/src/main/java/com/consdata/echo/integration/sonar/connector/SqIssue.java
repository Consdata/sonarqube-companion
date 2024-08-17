package com.consdata.echo.integration.sonar.connector;

import com.consdata.echo.issues.Issue;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SqIssue(
        String key,
        String component,
        String project,
        String severity,
        String author,
        String effort,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        LocalDateTime creationDate,
        String type
) {

    public static Issue toIssue(SqIssue sqIssue) {
        return new Issue(sqIssue.key);
    }
}
