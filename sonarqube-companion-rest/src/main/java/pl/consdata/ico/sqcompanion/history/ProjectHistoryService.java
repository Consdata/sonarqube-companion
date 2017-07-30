package pl.consdata.ico.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectHistoryService {

    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectHistoryService(
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
                        gr -> gr.getProjects()
                                .forEach(this::synProjectHistoryAndCatch)
                );
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

        // get measures
        final Optional<ProjectHistoryEntryEntity> lastStoredMeasure = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(project.getKey());
        final List<SonarQubeMeasure> historicAnalyses = sonarQubeFacade.getProjectMeasureHistory(
                project.getServerId(),
                project.getKey(),
                lastStoredMeasure.isPresent() ? lastStoredMeasure.get().getDate() : null
        );

        log.info("Project analyses to store [project={}, analyses={}]", project, historicAnalyses.size());
        if (!historicAnalyses.isEmpty()) {
            // combine measures by dates and use latest for each day
            final Map<LocalDate, SonarQubeMeasure> combined = combineToSingleMeasurePerDay(historicAnalyses);

            // calculate historic entry for each past day, use previous available if non available for analyzed day
            final List<ProjectHistoryEntryEntity> history = new ArrayList<>();
            SonarQubeMeasure lastMeasure = getFirstAvailableMeasure(combined);
            for (LocalDate date = asLocalDate(lastMeasure.getDate()); !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
                if (combined.containsKey(date)) {
                    lastMeasure = combined.get(date);
                }
                history.add(mapMeasureToHistoryEntry(date, project, lastMeasure));
            }

            // store
            projectHistoryRepository.saveAll(history);
        }
    }

    private SonarQubeMeasure getFirstAvailableMeasure(final Map<LocalDate, SonarQubeMeasure> combined) {
        return combined
                .values()
                .stream()
                .sorted(Comparator.comparing(SonarQubeMeasure::getDate))
                .findFirst()
                .orElseThrow(() -> new SQCompanionException("Can't find any measure"));
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
        return asLocalDate(measure.getDate());
    }

    private LocalDate asLocalDate(final Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private ProjectHistoryEntryEntity mapMeasureToHistoryEntry(final LocalDate date, final Project project, final SonarQubeMeasure measure) {
        final String combinedId = String.format(
                "%s$%s$%s",
                project.getServerId(),
                project.getKey(),
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
        return ProjectHistoryEntryEntity
                .builder()
                .id(combinedId)
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

}
