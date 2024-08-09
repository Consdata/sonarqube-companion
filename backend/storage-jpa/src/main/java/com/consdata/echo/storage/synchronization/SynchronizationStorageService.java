package com.consdata.echo.storage.synchronization;

import com.consdata.echo.synchronization.Status;
import com.consdata.echo.synchronization.SynchronizationStatusStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SynchronizationStorageService implements SynchronizationStatusStorage {
    private final SynchronizationStatusRepository synchronizationStatusRepository;

    @Override
    public void save(Status status) {
        synchronizationStatusRepository.save(StatusEntity.of(status));
    }

    @Override
    public Status status() {
        return StatusEntity.toStatus(synchronizationStatusRepository.findById("0").orElse(new StatusEntity()));
    }
}
