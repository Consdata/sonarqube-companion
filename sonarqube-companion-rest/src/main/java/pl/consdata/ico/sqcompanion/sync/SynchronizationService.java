package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import org.springframework.stereotype.Service;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class SynchronizationService {

	private final ProjectHistoryService projectHistoryService;
	private final RepositoryService repositoryService;

	public SynchronizationService(
			final ProjectHistoryService projectHistoryService,
			final RepositoryService repositoryService) {
		this.projectHistoryService = projectHistoryService;
		this.repositoryService = repositoryService;
	}

	public void runSynchronization() {
		repositoryService.syncGroups();
		projectHistoryService.syncProjectsHistory();
	}

}
