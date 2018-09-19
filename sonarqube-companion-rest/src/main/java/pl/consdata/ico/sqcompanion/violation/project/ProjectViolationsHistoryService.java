package pl.consdata.ico.sqcompanion.violation.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;
import pl.consdata.ico.sqcompanion.violation.ViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class ProjectViolationsHistoryService {

    private final RepositoryService repositoryService;
    private final SonarQubeFacade sonarQubeFacade;
    private final ProjectHistoryRepository projectHistoryRepository;

    public ProjectViolationsHistoryService(
            final RepositoryService repositoryService,
            final SonarQubeFacade sonarQubeFacade,
            final ProjectHistoryRepository projectHistoryRepository) {
        this.repositoryService = repositoryService;
        this.sonarQubeFacade = sonarQubeFacade;
        this.projectHistoryRepository = projectHistoryRepository;
    }

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
                .flatMap(project -> getProjectViolationsHistory(project, daysLimit).getViolationHistoryEntries().stream())
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

    @Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_DIFF_CACHE, sync = true, key = "#group.uuid + #fromDate + #toDate")
    public GroupViolationsHistoryDiff getGroupViolationsHistoryDiff(final Group group, final LocalDate fromDate, final LocalDate toDate) {
        final List<ProjectViolationsHistoryDiff> projectDiffs = group
                .getAllProjects()
                .stream()
                .filter(project -> projectHistoryRepository.existsByProjectKey(project.getKey()))
                .map(getProjectViolationsHistoryDiffMappingFunction(fromDate, toDate))
                .collect(Collectors.toList());

        final Violations addedViolations = Violations.builder().build();
        final Violations removedViolations = Violations.builder().build();
        projectDiffs
                .stream()
                .map(ProjectViolationsHistoryDiff::getViolationsDiff)
                .forEach(violations -> mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violations));
        return GroupViolationsHistoryDiff
                .builder()
                .groupDiff(Violations.sumViolations(addedViolations, removedViolations))
                .addedViolations(addedViolations)
                .removedViolations(removedViolations)
                .projectDiffs(projectDiffs)
                .build();
    }

    @Cacheable(value = Caches.PROJECT_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#project.getId() + #daysLimit")
    public ViolationsHistory getProjectViolationsHistory(final Project project, Optional<Integer> daysLimit) {
        List<ProjectHistoryEntryEntity> history;
        if (daysLimit.isPresent()) {
            history = projectHistoryRepository.findAllByProjectKeyAndDateGreaterThanEqual(
                    project.getKey(),
                    LocalDate.now().minusDays(daysLimit.get()));
        } else {
            history = projectHistoryRepository.findAllByProjectKey(project.getKey());
        }
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(
                        history.stream()
                                .map(this::asViolationHistoryEntry)
                                .collect(Collectors.toList())).build();
    }

    @Cacheable(value = Caches.PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE, sync = true, key = "#project.getId() + #fromDate + #toDate")
    public ProjectViolationsHistoryDiff getProjectViolationsHistoryDiff(final Project project, final LocalDate fromDate, final LocalDate toDate) {
        return getProjectViolationsHistoryDiffMappingFunction(fromDate, toDate).apply(project);
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
        final List<SonarQubeMeasure> historicAnalyses = sonarQubeFacade.projectMeasureHistory(
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

            log.info("Storing project historic analyses [analysesToStore={}, project={}]", history.size(), project);
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

    private Function<Project, ProjectViolationsHistoryDiff> getProjectViolationsHistoryDiffMappingFunction(LocalDate fromDate, LocalDate toDate) {
        return project -> {
            final Optional<ProjectHistoryEntryEntity> fromDateEntryOptional =
                    projectHistoryRepository
                            .findByProjectKeyAndDateEquals(project.getKey(), fromDate);
            final Optional<ProjectHistoryEntryEntity> toDateEntryOptional = projectHistoryRepository
                    .findByProjectKeyAndDateEquals(project.getKey(), toDate);

            if (fromDateEntryOptional.isPresent() && !toDateEntryOptional.isPresent()) {
                throw new SQCompanionException(
                        String.format(
                                "Can't get diff for project with history and without to date analyses [projectKey=%s]",
                                project.getKey()
                        )
                );
            }
            final ProjectHistoryEntryEntity fromDateEntry = fromDateEntryOptional.orElse(ProjectHistoryEntryEntity.empty());
            final ProjectHistoryEntryEntity toDateEntry = toDateEntryOptional.orElse(ProjectHistoryEntryEntity.empty());

            final Violations violationsDiff = Violations
                    .builder()
                    .blockers(toDateEntry.getBlockers() - fromDateEntry.getBlockers())
                    .criticals(toDateEntry.getCriticals() - fromDateEntry.getCriticals())
                    .majors(toDateEntry.getMajors() - fromDateEntry.getMajors())
                    .minors(toDateEntry.getMinors() - fromDateEntry.getMinors())
                    .infos(toDateEntry.getInfos() - fromDateEntry.getInfos())
                    .build();
            return ProjectViolationsHistoryDiff
                    .builder()
                    .projectId(project.getId())
                    .fromDate(fromDate)
                    .toDate(toDate)
                    .projectKey(project.getKey())
                    .violationsDiff(violationsDiff)
                    .build();
        };
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
                                .blockers(ofNullable(entry.getBlockers()).orElse(0))
                                .criticals(ofNullable(entry.getCriticals()).orElse(0))
                                .majors(ofNullable(entry.getMajors()).orElse(0))
                                .minors(ofNullable(entry.getMinors()).orElse(0))
                                .infos(ofNullable(entry.getInfos()).orElse(0))
                                .build()
                )
                .build();
    }

    private void mergeProjectViolationsToAddedOrRemovedGroupViolations(
            final Violations addedViolations,
            final Violations removedViolations,
            final Violations violations) {
        if (violations.getBlockers() >= 0) {
            addedViolations.addBlockers(violations.getBlockers());
        } else {
            removedViolations.addBlockers(Math.abs(violations.getBlockers()));
        }
        if (violations.getCriticals() >= 0) {
            addedViolations.addCriticals(violations.getCriticals());
        } else {
            removedViolations.addCriticals(Math.abs(violations.getCriticals()));
        }
        if (violations.getMajors() >= 0) {
            addedViolations.addMajors(violations.getMajors());
        } else {
            removedViolations.addMajors(Math.abs(violations.getMajors()));
        }
        if (violations.getMinors() >= 0) {
            addedViolations.addMinors(violations.getMinors());
        } else {
            removedViolations.addMinors(Math.abs(violations.getMinors()));
        }
        if (violations.getInfos() >= 0) {
            addedViolations.addInfos(violations.getInfos());
        } else {
            removedViolations.addInfos(Math.abs(violations.getInfos()));
        }
    }

}
