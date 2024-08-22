package com.consdata.echo.issues;

import java.time.LocalDateTime;

public record Issue(
        String id,
//        String component,
//        String source,
//        IssueType type,
//        String severity,
//        String author,
        LocalDateTime createdAt
) {
}
