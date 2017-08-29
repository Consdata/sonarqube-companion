package pl.consdata.ico.sqcompanion.sync;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author pogoma
 */
public interface SynchronizationStateRepository extends JpaRepository<SynchronizationStateEntity, Long> {

    SynchronizationStateEntity findFirstByOrderByIdDesc();

    @Query("SELECT AVG(p.duration) FROM synchronization AS p WHERE p.id > (SELECT MAX(s.id) - 5 FROM synchronization AS s) AND p.duration IS NOT NULL")
    Double findAverageDurationOfLastSynchronizations();

}
