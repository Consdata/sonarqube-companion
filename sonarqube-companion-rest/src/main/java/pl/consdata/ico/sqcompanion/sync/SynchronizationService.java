package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.users.UsersService;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryService;
import pl.consdata.ico.sqcompanion.project.ProjectService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserViolationDiffSyncService;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistorySyncService;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class SynchronizationService {

    private final ProjectService projectService;
    private final ProjectViolationsHistoryService projectViolationsHistoryService;
    private final UserViolationDiffSyncService userViolationDiffSyncService;
    private final UsersService usersService;
    private final RepositoryService repositoryService;
    private final UserViolationSummaryHistorySyncService userViolationSummaryHistorySyncService;
    private final SynchronizationStateService synchronizationStateService;
    private final CacheManager cacheManager;
    private final Semaphore semaphore = new Semaphore(1);

    public SynchronizationService(
            final ProjectService projectService,
            final ProjectViolationsHistoryService projectViolationsHistoryService,
            final UserViolationDiffSyncService userViolationDiffSyncService,
            final UsersService usersService,
            final RepositoryService repositoryService,
            final UserViolationSummaryHistorySyncService userViolationSummaryHistorySyncService,
            final SynchronizationStateService synchronizationStateService,
            final CacheManager cacheManager) {
        this.projectService = projectService;
        this.projectViolationsHistoryService = projectViolationsHistoryService;
        this.userViolationDiffSyncService = userViolationDiffSyncService;
        this.usersService = usersService;
        this.repositoryService = repositoryService;
        this.userViolationSummaryHistorySyncService = userViolationSummaryHistorySyncService;
        this.synchronizationStateService = synchronizationStateService;
        this.cacheManager = cacheManager;
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
        usersService.sync();
        projectViolationsHistoryService.syncProjectsHistory();
        userViolationSummaryHistorySyncService.sync();
        userViolationDiffSyncService.sync();
        synchronizationStateService.finishSynchronization();

        Caches.LIST
                .stream()
                .map(cacheManager::getCache)
                .forEach(cache -> cache.clear());

        log.info("Synchronization finished in {}ms", (System.currentTimeMillis() - startTime));
    }

}
