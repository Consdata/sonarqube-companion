package com.consdata.echo.synchronization;

import com.consdata.echo.api.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("synchronization")
@RequiredArgsConstructor
@Slf4j
public class SynchronizationController {
    private final SynchronizationStatusStorage statusStorage;
    private final SynchronizationRunner runner;


    @GetMapping("start")
    @PreAuthorize("hasAuthority('" + Authorities.TRIGGER_SYNCHRONIZATION + "')")
    public Status start() {
        log.info("Synchronization triggered manually");
        Status status = statusStorage.status();
        if (!status.pending()) {
            runner.sync();
            return statusStorage.status();
        }
        return status;
    }

    @GetMapping("status")
    @PreAuthorize("hasAuthority('" + Authorities.CHECK_SYNCHRONIZATION_STATUS + "')")
    public Status status() {
        return statusStorage.status();
    }
}
