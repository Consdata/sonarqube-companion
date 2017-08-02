package pl.consdata.ico.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ViolationsHistoryService {

    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final ProjectHistoryRepository projectHistoryRepository;

    public ViolationsHistoryService(
            final RepositoryService repositoryService,
            final SonarQubeFacade sonarQubeFacade,
            final ProjectHistoryRepository projectHistoryRepository) {
        this.repositoryService = repositoryService;
        this.sonarQubeFacade = sonarQubeFacade;
        this.projectHistoryRepository = projectHistoryRepository;
    }

    @Transactional
    public void syncProjectsHistory() {
        repositoryService
                .getRootGroup()
                .accept(
                        gr -> gr.getProjects().forEach(this::synProjectHistoryAndCatch)
                );
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#group.uuid + #daysLimit")
    public ViolationsHistory getGroupViolationsHistory(final Group group, Optional<Integer> daysLimit) {
        final List<ViolationHistoryEntry> history = group
                .getAllProjects()
                .stream()
                .flatMap(project -> getProjectViolationsHistory(project, daysLimit).stream())
                .map(this::asViolationHistoryEntry)
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
                .sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
                .collect(Collectors.toList());
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(history)
                .build();
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#project.getId() + #daysLimit")
    public List<ProjectHistoryEntryEntity> getProjectViolationsHistory(final Project project, Optional<Integer> daysLimit) {
        if (daysLimit.isPresent()) {
            return projectHistoryRepository.findAllByProjectKeyAndDateGreaterThanEqual(
                    project.getKey(),
                    LocalDate.now().minusDays(daysLimit.get())
            );
        } else {
            return projectHistoryRepository.findAllByProjectKey(project.getKey());
        }
    }

    private void synProjectHistoryAndCatch(final Project project) {
        try {
            synProjectHistory(project);
        } catch (final Exception exception) {
            log.error("Project history synchronization failed [project={}]", project, exception);
        }
    }

    private void synProjectHistory(final Project project) {
        log.info("Syncing project history [project={}]", project);

        final Optional<ProjectHistoryEntryEntity> lastStoredMeasure = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(project.getKey());
        final List<SonarQubeMeasure> historicAnalyses = sonarQubeFacade.getProjectMeasureHistory(
                project.getServerId(),
                project.getKey(),
                lastStoredMeasure.isPresent() ? lastStoredMeasure.get().getDate() : null
        );

        if (!historicAnalyses.isEmpty() || lastStoredMeasure.isPresent()) {
            final Map<LocalDate, SonarQubeMeasure> combined = combineToSingleMeasurePerDay(historicAnalyses);
            final LocalDate startDate = !historicAnalyses.isEmpty() ? LocalDateUtil.asLocalDate(historicAnalyses.get(0).getDate()) : lastStoredMeasure.get().getDate();

            final List<ProjectHistoryEntryEntity> history = new ArrayList<>();
            SonarQubeMeasure lastMeasure = !historicAnalyses.isEmpty() ? historicAnalyses.get(0) : asSonarQubeMeasure(lastStoredMeasure.get());
            for (LocalDate date = startDate; !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
                if (combined.containsKey(date)) {
                    lastMeasure = combined.get(date);
                }
                history.add(asProjectHistoryEntryEntity(date, project, lastMeasure));
            }

            projectHistoryRepository.saveAll(history);
        } else {
            log.info("Project has neither history nor analyses, skipping [projectId={}]", project.getId());
        }
    }

    private Map<LocalDate, SonarQubeMeasure> combineToSingleMeasurePerDay(final List<SonarQubeMeasure> historicAnalyses) {
        return historicAnalyses
                .stream()
                .collect(Collectors.groupingBy(
                        this::getLocalDate,
                        Collectors.reducing(this::useLaterMeasure)
                ))
                .entrySet()
                .stream()
                .collect(mapOptionalValuesToValues());
    }

    private Collector<Map.Entry<LocalDate, Optional<SonarQubeMeasure>>, ?, Map<LocalDate, SonarQubeMeasure>> mapOptionalValuesToValues() {
        return Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get());
    }

    private SonarQubeMeasure useLaterMeasure(final SonarQubeMeasure a, final SonarQubeMeasure b) {
        return a.getDate().after(b.getDate()) ? a : b;
    }

    private LocalDate getLocalDate(final SonarQubeMeasure measure) {
        return LocalDateUtil.asLocalDate(measure.getDate());
    }

    private ProjectHistoryEntryEntity asProjectHistoryEntryEntity(final LocalDate date, final Project project, final SonarQubeMeasure measure) {
        return ProjectHistoryEntryEntity
                .builder()
                .id(ProjectHistoryEntryEntity.combineId(project.getServerId(), project.getKey(), date))
                .serverId(project.getServerId())
                .projectKey(project.getKey())
                .date(date)
                .blockers(measure.getBlockers())
                .criticals(measure.getCriticals())
                .majors(measure.getMajors())
                .minors(measure.getMinors())
                .infos(measure.getInfos())
                .build();
    }

    private SonarQubeMeasure asSonarQubeMeasure(final ProjectHistoryEntryEntity entryEntity) {
        return SonarQubeMeasure
                .builder()
                .date(LocalDateUtil.asDate(entryEntity.getDate()))
                .blockers(entryEntity.getBlockers())
                .criticals(entryEntity.getCriticals())
                .majors(entryEntity.getMajors())
                .minors(entryEntity.getMinors())
                .infos(entryEntity.getInfos())
                .build();
    }

    private ViolationHistoryEntry asViolationHistoryEntry(final ProjectHistoryEntryEntity entry) {
        return ViolationHistoryEntry
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
                .build();
    }

}
