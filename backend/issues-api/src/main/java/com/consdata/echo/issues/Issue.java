package com.consdata.echo.issues;

import java.time.LocalDate;

public record Issue(
        String id,
        String component,
        String type,
        String source,
        String description,
        String severity,
        String author,
        String assignee,
        LocalDate created,
        LocalDate modified
) {
}
