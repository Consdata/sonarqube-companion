package pl.consdata.ico.sqcompanion.sync;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SynchronizationState getCurrentState() {
        return this.synchronizationStateRepository.findFirstByOrderByIdDesc();
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void initSynchronization(long tasks) {
        this.synchronizationStateRepository.saveAndFlush(SynchronizationState.builder().startTimestamp(System.currentTimeMillis()).allTasks(tasks).build());
    }

    @Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
    public void addFailedTask() {
        SynchronizationState currentState = getCurrentState();
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
        SynchronizationState currentState = getCurrentState();
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
        SynchronizationState currentState = getCurrentState();
        if (currentState.getFinishTimestamp() != null) {
            return;
        }
        currentState.setFinishTimestamp(System.currentTimeMillis());
        this.synchronizationStateRepository.saveAndFlush(currentState);
    }

}
