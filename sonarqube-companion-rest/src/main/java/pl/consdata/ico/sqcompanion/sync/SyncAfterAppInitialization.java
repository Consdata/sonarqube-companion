package pl.consdata.ico.sqcompanion.sync;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

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

	/**
	 * TODO (mp): to nie powinno blokować wstawania aplikacji
	 */
	@PostConstruct
	public void tickSynchronizationAfterAppInit() {
		synchronizationService.runSynchronization();
	}

}
