package pl.consdata.ico.sqcompanion.sync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class SynchronizationStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long startTimestamp;
    private Long finishTimestamp;
    private Long allTasks;
    private Long finishedTasks;
    private Long failedTasks;

}
