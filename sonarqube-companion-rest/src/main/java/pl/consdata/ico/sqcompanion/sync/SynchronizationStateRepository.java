package pl.consdata.ico.sqcompanion.sync;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author pogoma
 */
public interface SynchronizationStateRepository extends JpaRepository<SynchronizationState, Long> {

    SynchronizationState findFirstByOrderByIdDesc();

}
