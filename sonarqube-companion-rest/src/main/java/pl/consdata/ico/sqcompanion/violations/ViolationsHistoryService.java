package pl.consdata.ico.sqcompanion.violations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ViolationsHistoryService {

    private final ProjectHistoryRepository projectHistoryRepository;

    public ViolationsHistoryService(final ProjectHistoryRepository projectHistoryRepository) {
        this.projectHistoryRepository = projectHistoryRepository;
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#group.uuid + #daysLimit")
    public ViolationsHistory getGroupViolationsHistory(final Group group, Optional<Integer> daysLimit) {
        final List<ViolationHistoryEntry> history = group
                .getAllProjects()
                .stream()
                .flatMap(project -> getProjectViolationsHistory(project, daysLimit).stream())
                .map(
                        entry -> ViolationHistoryEntry
                                .builder()
                                .date(entry.getDate())
                                .violations(
                                        Violations
                                                .builder()
                                                .blockers(entry.getBlockers())
                                                .criticals(entry.getCriticals())
                                                .majors(entry.getMajors())
                                                .minors(entry.getMinors())
                                                .infos(entry.getInfos())
                                                .build()
                                )
                                .build()
                )
                .collect(
                        Collectors.groupingBy(
                                ViolationHistoryEntry::getDate,
                                Collectors.reducing(ViolationHistoryEntry::sumEntries)
                        )
                )
                .values()
                .stream()
                .filter(entry -> entry.isPresent())
                .map(entry -> entry.get())
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
                .collect(Collectors.toList());
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(history)
                .build();
    }

    private List<ProjectHistoryEntryEntity> getProjectViolationsHistory(final Project project, final Optional<Integer> daysLimit) {
        if (daysLimit.isPresent()) {
            return projectHistoryRepository.findAllByProjectKeyAndDateGreaterThanEqual(
                    project.getKey(),
                    LocalDate.now().minusDays(daysLimit.get())
            );
        } else {
            return projectHistoryRepository.findAllByProjectKey(project.getKey());
        }
    }

}
