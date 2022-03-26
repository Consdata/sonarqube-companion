package pl.consdata.ico.sqcompanion.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.project.ProjectService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.users.UsersService;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryService;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserViolationDiffSyncService;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistorySyncService;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author gregorry
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SynchronizationService {
    private final ProjectService projectService;
    private final ProjectViolationsHistoryService projectViolationsHistoryService;
    private final UserViolationDiffSyncService userViolationDiffSyncService;
    private final UsersService usersService;
    private final RepositoryService repositoryService;
    private final UserViolationSummaryHistorySyncService userViolationSummaryHistorySyncService;
    private final SynchronizationStateService synchronizationStateService;
    private final CacheManager cacheManager;
    private final MemberService memberService;
    private final PrepopulateCacheService prepopulateCacheService;
    private final Semaphore semaphore = new Semaphore(1);

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
        memberService.syncMembers();
        usersService.sync();
        projectViolationsHistoryService.syncProjectsHistory();
        userViolationDiffSyncService.sync();
        userViolationSummaryHistorySyncService.sync();
        synchronizationStateService.finishSynchronization();
        Caches.LIST
                .stream()
                .map(cacheManager::getCache)
                .forEach(Cache::clear);
        prepopulateCacheService.prepopulate();

        log.info("Synchronization finished in {}ms", (System.currentTimeMillis() - startTime));
    }

}
