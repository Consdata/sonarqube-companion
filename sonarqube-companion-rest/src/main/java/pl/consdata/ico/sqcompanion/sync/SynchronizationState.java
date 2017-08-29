package pl.consdata.ico.sqcompanion.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SynchronizationState {
    private Long startTimestamp;
    private Long finishTimestamp;
    private Long allTasks;
    private Long finishedTasks;
    private Long failedTasks;
    private Long duration;
    private Double estimatedDuration;
    private boolean finished;
    private Long currentServerTimestamp;
}
