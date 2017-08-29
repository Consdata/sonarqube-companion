package pl.consdata.ico.sqcompanion.sync;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author pogoma
 */
@Service
public class SynchronizationStateService {

    private final SynchronizationStateRepository synchronizationStateRepository;

    public SynchronizationStateService(SynchronizationStateRepository synchronizationStateRepository) {
        this.synchronizationStateRepository = synchronizationStateRepository;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public SynchronizationStateEntity getCurrentState() {
        return this.synchronizationStateRepository.findFirstByOrderByIdDesc();
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void initSynchronization() {
        this.synchronizationStateRepository.saveAndFlush(
                SynchronizationStateEntity
                        .builder()
                        .startTimestamp(System.currentTimeMillis())
                        .build()
        );
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void addFailedTask() {
        SynchronizationStateEntity currentState = getCurrentState();
        if (currentState.getFinishTimestamp() != null) {
            return;
        }
        if (currentState.getFailedTasks() == null) {
            currentState.setFailedTasks(1L);
        } else {
            currentState.setFailedTasks(currentState.getFailedTasks() + 1);
        }
        this.synchronizationStateRepository.saveAndFlush(currentState);
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void addFinishedTask() {
        SynchronizationStateEntity currentState = getCurrentState();
        if (currentState.getFinishTimestamp() != null) {
            return;
        }
        if (currentState.getFinishedTasks() == null) {
            currentState.setFinishedTasks(1L);
        } else {
            currentState.setFinishedTasks(currentState.getFinishedTasks() + 1);
        }
        this.synchronizationStateRepository.saveAndFlush(currentState);
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void finishSynchronization() {
        SynchronizationStateEntity currentState = getCurrentState();
        if (currentState.getFinishTimestamp() != null) {
            return;
        }
        currentState.setFinishTimestamp(System.currentTimeMillis());
        currentState.setDuration(currentState.getFinishTimestamp() - currentState.getStartTimestamp());
        if (currentState.getFailedTasks() == null) {
            currentState.setFailedTasks(0L);
        }
        if (currentState.getFinishedTasks() == null) {
            currentState.setFinishedTasks(0L);
        }
        currentState.setAllTasks(currentState.getFailedTasks() + currentState.getFinishedTasks());
        this.synchronizationStateRepository.saveAndFlush(currentState);
    }

    @Transactional
    public double estimatedSynchronizationTime() {
        return Optional.ofNullable(this.synchronizationStateRepository.findAverageDurationOfLastSynchronizations()).orElse(1000.0);
    }

}
