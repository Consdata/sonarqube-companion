package com.consdata.echo.synchronization;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class SynchronizationRunner {
    private final List<SynchronizationManager> managers;
    private final SynchronizationStatusStorage statusStorage;

    @PostConstruct
    public void sync() {
        statusStorage.save(new Status(0, true));
        managers.stream().sorted(comparing(SynchronizationManager::order))
                .toList().forEach(SynchronizationManager::sync);
        statusStorage.save(new Status(100, false));
    }
}
