package pl.consdata.ico.sqcompanion.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByGroupIdAndDateBetween(String groupId, LocalDate from, LocalDate to);

    List<Event> findAllByGlobalIsTrueAndDateBetween(LocalDate from, LocalDate to);

}
