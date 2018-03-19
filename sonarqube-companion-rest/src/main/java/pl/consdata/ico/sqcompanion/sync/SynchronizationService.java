package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.history.ViolationsHistoryService;
import pl.consdata.ico.sqcompanion.project.ProjectService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.statistics.StatisticsService;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class SynchronizationService {

    private final ProjectService projectService;
    private final ViolationsHistoryService violationsHistoryService;
    private final RepositoryService repositoryService;
    private final SynchronizationStateService synchronizationStateService;
    private final CacheManager cacheManager;
    private final StatisticsService statisticsService;
    private final Semaphore semaphore = new Semaphore(1);

    public SynchronizationService(
            final ProjectService projectService,
            final ViolationsHistoryService violationsHistoryService,
            final RepositoryService repositoryService,
            final SynchronizationStateService synchronizationStateService,
            final CacheManager cacheManager,
            final StatisticsService statisticsService) {
        this.projectService = projectService;
        this.violationsHistoryService = violationsHistoryService;
        this.repositoryService = repositoryService;
        this.synchronizationStateService = synchronizationStateService;
        this.cacheManager = cacheManager;
        this.statisticsService = statisticsService;
    }

    public void acquireAndStartSynchronization() throws SynchronizationException {
        boolean permit;
        try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new CannotStartSynchronizationException();
        }
        if (permit) {
            try {
                synchronize();
            } finally {
                semaphore.release();
            }
        } else {
            throw new SynchronizationInProgressException();
        }
    }

    /**
     * Always run within semaphore loc via {@link SynchronizationService#acquireAndStartSynchronization()}!
     */
    private void synchronize() {
        log.info("Starting synchronization... This may take a while.");
        long startTime = System.currentTimeMillis();

        synchronizationStateService.initSynchronization();
        projectService.syncProjects();
        repositoryService.syncGroups();
        violationsHistoryService.syncProjectsHistory();
        synchronizationStateService.finishSynchronization();
        statisticsService.syncStatistics();


        Caches.LIST
                .stream()
                .map(cacheManager::getCache)
                .forEach(cache -> cache.clear());

        log.info("Synchronization finished in {}ms", (System.currentTimeMillis() - startTime));
    }

}
