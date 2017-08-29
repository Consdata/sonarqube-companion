package pl.consdata.ico.sqcompanion.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author pogoma
 */
@Entity(name = "synchronization")
@Table
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "synchronization_id_seq", sequenceName = "synchronization_id_seq", allocationSize = 1)
public class SynchronizationStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "synchronization_id_seq")
    private Long id;
    @NotNull
    private Long startTimestamp;
    private Long finishTimestamp;
    private Long allTasks;
    private Long finishedTasks;
    private Long failedTasks;
    private Long duration;

}
