package pl.consdata.ico.sqcompanion.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public void addEvent(Event event) {
        repository.save(event);
    }

    public List<Event> getByGroup(String groupId, LocalDate from, LocalDate to) {
        List<Event> output = new ArrayList<>(repository.findAllByGroupIdAndDateBetween(groupId, from, to));
        output.addAll(repository.findAllByGlobalIsTrueAndDateBetween(from, to));
        return output;
    }

    public List<Event> getByGroup(String groupId, Optional<Integer> daysLimit) {
        List<Event> output = new ArrayList<>(repository.findAllByGroupIdAndDateBetween(groupId, LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1)));
        output.addAll(repository.findAllByGlobalIsTrueAndDateBetween(LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1)));
        return output;
    }
}
