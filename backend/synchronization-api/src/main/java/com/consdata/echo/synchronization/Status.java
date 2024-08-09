package com.consdata.echo.synchronization;


public record Status(
        int progress,
        boolean pending
) {
}
