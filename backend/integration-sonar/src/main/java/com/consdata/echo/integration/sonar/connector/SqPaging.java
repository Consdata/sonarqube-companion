package com.consdata.echo.integration.sonar.connector;

public record SqPaging(
        int pageIndex,
        int pageSize,
        long total
) {
}
