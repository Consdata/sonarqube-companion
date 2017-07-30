package pl.consdata.ico.sqcompanion.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author pogoma
 */
@Service
@Slf4j
public class SynchronizationTrigger {

    private final TaskScheduler taskScheduler;
    private final SynchronizationService synchronizationService;
    private final AppConfig configuration;
    private ScheduledFuture<?> lastTask;

    public SynchronizationTrigger(final TaskScheduler taskScheduler,
                                  final SynchronizationService synchronizationService,
                                  final AppConfig configuration) {
        this.taskScheduler = taskScheduler;
        this.synchronizationService = synchronizationService;
        this.configuration = configuration;
    }

    public void scheduleTaskImmediately() {
        lastTask = taskScheduler.schedule(this::run, new Date());
    }

    @PreDestroy
    public void cleanup() {
        if (this.lastTask != null && !this.lastTask.isCancelled()) {
            this.lastTask.cancel(false);
        }
    }

    private void run() {
        try {
            synchronizationService.acquireAndStartSynchronization();
        } catch (SynchronizationException e) {
            log.warn("Cannot run synchronization: {}", e.getMessage(), e);
        } finally {
            scheduleNextTask();
        }
    }

    private void scheduleNextTask() {
        TimeUnit timeUnit = configuration.getScheduler().getTimeUnit();
        final long time = configuration.getScheduler().getInterval();
        lastTask = taskScheduler.schedule(this::run, new Date(System.currentTimeMillis() + timeUnit.toMillis(time)));
    }

}
