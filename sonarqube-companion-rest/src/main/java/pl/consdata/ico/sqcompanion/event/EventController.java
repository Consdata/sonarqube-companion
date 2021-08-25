package pl.consdata.ico.sqcompanion.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/{from}/{to}/group/{groupId}")
    public List<Event> getByGroup(@PathVariable String groupId, @PathVariable LocalDate from, @PathVariable LocalDate to) {
        return eventService.getByGroup(groupId, from, to);
    }

    @GetMapping("/group/{groupId}")
    public List<Event> getByGroup(@PathVariable String groupId, @RequestParam Optional<Integer> daysLimit) {
        return eventService.getByGroup(groupId, daysLimit);
    }
}
