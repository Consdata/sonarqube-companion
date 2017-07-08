package net.lipecki.sqcompanion.sync;

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

	private final SynchronizationService synchronizationService;

	public SyncAfterAppInitialization(final SynchronizationService synchronizationService) {
		this.synchronizationService = synchronizationService;
	}

	@PostConstruct
	public void tickSynchronizationAfterAppInit() {
		synchronizationService.runSynchronization();
	}

}
