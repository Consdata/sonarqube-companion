package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class SynchronizationService {

    private final ProjectHistoryService projectHistoryService;
    private final RepositoryService repositoryService;
    private final SynchronizationStateService synchronizationStateService;
    private final Semaphore semaphore = new Semaphore(1);

    public SynchronizationService(
            final ProjectHistoryService projectHistoryService,
            final RepositoryService repositoryService,
            SynchronizationStateService synchronizationStateService) {
        this.projectHistoryService = projectHistoryService;
        this.repositoryService = repositoryService;
        this.synchronizationStateService = synchronizationStateService;
    }

    public synchronized void runSynchronization() throws SynchronizationException {
        boolean permit;
        try {
            permit = semaphore.tryAcquire(1, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new CannotStartSynchronizationException();
        }
        if (permit) {
            try {
                repositoryService.syncGroups();
                synchronizationStateService.initSynchronization(getProjectsCount());
                projectHistoryService.syncProjectsHistory();
                synchronizationStateService.finishSynchronization();
            } finally {
                semaphore.release();
            }
        } else {
            throw new SynchronizationInProgressException();
        }
    }

    private long getProjectsCount() {
        AllProjectsCounter counter = new AllProjectsCounter();
        repositoryService.getRootGroup().accept(counter);
        return counter.getCount();
    }

}
