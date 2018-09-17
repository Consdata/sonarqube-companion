package pl.consdata.ico.sqcompanion.project;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class ProjectSummaryService {

    private final ProjectHistoryRepository projectHistoryRepository;
    private final HealthCheckService healthCheckService;
    private final MeterRegistry meterRegistry;

    public ProjectSummaryService(
            final ProjectHistoryRepository projectHistoryRepository,
            final HealthCheckService healthCheckService,
            final MeterRegistry meterRegistry) {
        this.projectHistoryRepository = projectHistoryRepository;
        this.healthCheckService = healthCheckService;
        this.meterRegistry = meterRegistry;
    }

    @Cacheable(value = Caches.PROJECT_SUMMARY_CACHE, sync = true, key = "#project.key")
    public ProjectSummary getProjectSummary(final Project project) {
        return asProjectSummary(project);
    }

    public List<ProjectSummary> getProjectSummaries(final List<Project> allProjects) {
        return allProjects
                .stream()
                .map(this::asProjectSummary)
                .collect(Collectors.toList());
    }

    private ProjectSummary asProjectSummary(final Project p) {
        meterRegistry.counter("services.ProjectSummaryService.asProjectSummary.count").increment();
        long startTime = System.currentTimeMillis();

        final ProjectHistoryEntryEntity historyEntry = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(p.getKey()).orElse(null);
        final ProjectSummary.ProjectSummaryBuilder builder = ProjectSummary
                .builder()
                .name(p.getName())
                .key(p.getKey())
                .serverId(p.getServerId())
                .serverUrl(p.getServerUrl())
                .healthStatus(healthCheckService.checkHealth(p));
        if (historyEntry != null) {
            builder.violations(
                    Violations
                            .builder()
                            .blockers(historyEntry.getBlockers())
                            .criticals(historyEntry.getCriticals())
                            .majors(historyEntry.getMajors())
                            .minors(historyEntry.getMinors())
                            .infos(historyEntry.getInfos())
                            .build()
            );
        }

        meterRegistry.gauge("services.ProjectSummaryService.asProjectSummary.time", System.currentTimeMillis() - startTime);
        return builder.build();
    }

}
