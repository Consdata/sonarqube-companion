package pl.consdata.ico.sqcompanion.sync;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pogoma
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/sync")
public class SynchronizationController {

    private final SynchronizationStateService synchronizationStateService;
    private final SynchronizationTrigger synchronizationTrigger;

    public SynchronizationController(final SynchronizationStateService synchronizationStateService,
                                     final SynchronizationTrigger synchronizationTrigger) {
        this.synchronizationStateService = synchronizationStateService;
        this.synchronizationTrigger = synchronizationTrigger;
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Returns state of current synchronization of projects history"
    )
    public SynchronizationState getState() {
        SynchronizationStateEntity currentState = synchronizationStateService.getCurrentState();

        return SynchronizationState
                .builder()
                .estimatedDuration(synchronizationStateService.estimatedSynchronizationTime())
                .allTasks(currentState.getAllTasks())
                .duration(currentState.getDuration())
                .finishedTasks(currentState.getFinishedTasks())
                .allTasks(currentState.getAllTasks())
                .failedTasks(currentState.getFailedTasks())
                .startTimestamp(currentState.getStartTimestamp())
                .finishTimestamp(currentState.getFinishTimestamp())
                .finished(currentState.getFinishTimestamp() != null)
                .currentServerTimestamp(System.currentTimeMillis())
                .build();
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Starting new synchronization of projects history"
    )
    public SynchronizationState startSynchronization() {
        synchronizationTrigger.scheduleTaskImmediately();
        return SynchronizationState
                .builder()
                .estimatedDuration(synchronizationStateService.estimatedSynchronizationTime())
                .finished(false)
                .startTimestamp(System.currentTimeMillis())
                .currentServerTimestamp(System.currentTimeMillis())
                .build();
    }

}
