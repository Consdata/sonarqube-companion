package com.consdata.echo.synchronization;

public interface SynchronizationManager {
    void sync();

    default Integer order() {
        return 0;
    }
}
