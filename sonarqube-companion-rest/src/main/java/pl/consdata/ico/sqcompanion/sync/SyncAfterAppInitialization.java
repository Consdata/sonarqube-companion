package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author gregorry
 */
@Slf4j
@Service
@Profile("default")
public class SyncAfterAppInitialization {

    private final SynchronizationTrigger synchronizationTrigger;

    public SyncAfterAppInitialization(final SynchronizationTrigger synchronizationTrigger) {
        this.synchronizationTrigger = synchronizationTrigger;
    }

    @PostConstruct
    public void tickSynchronizationAfterAppInit() {
        synchronizationTrigger.scheduleTaskImmediately();
    }

}
