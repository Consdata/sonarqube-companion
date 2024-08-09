package com.consdata.echo.synchronization;

public interface SynchronizationStatusStorage {
    void save(Status status);

    Status status();
}
