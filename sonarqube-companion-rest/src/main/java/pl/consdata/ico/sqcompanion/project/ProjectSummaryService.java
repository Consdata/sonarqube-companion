package pl.consdata.ico.sqcompanion.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.history.Violations;

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
    private final CounterService counterService;
    private final GaugeService gaugeService;

    public ProjectSummaryService(
            final ProjectHistoryRepository projectHistoryRepository,
            final HealthCheckService healthCheckService,
            final CounterService counterService,
            final GaugeService gaugeService) {
        this.projectHistoryRepository = projectHistoryRepository;
        this.healthCheckService = healthCheckService;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    public List<ProjectSummary> getProjectSummaries(final List<Project> allProjects) {
        return allProjects
                .stream()
                .map(this::asProjectSummary)
                .collect(Collectors.toList());
    }

    private ProjectSummary asProjectSummary(final Project p) {
        counterService.increment("services.ProjectSummaryService.asProjectSummary");
        long startTime = System.currentTimeMillis();

        final ProjectHistoryEntryEntity historyEntry = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(p.getKey()).orElse(null);
        final ProjectSummary.ProjectSummaryBuilder builder = ProjectSummary
                .builder()
                .name(p.getName())
                .key(p.getKey())
                .serverId(p.getServerId())
                .url(p.getUrl())
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

        gaugeService.submit("services.ProjectSummaryService.asProjectSummary", System.currentTimeMillis() - startTime);
        return builder.build();
    }

}
