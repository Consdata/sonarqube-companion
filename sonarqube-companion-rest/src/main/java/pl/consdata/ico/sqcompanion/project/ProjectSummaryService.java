package pl.consdata.ico.sqcompanion.project;

import lombok.extern.slf4j.Slf4j;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryEntry;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.violations.Violations;
import org.springframework.stereotype.Service;

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

	public ProjectSummaryService(
			final ProjectHistoryRepository projectHistoryRepository,
			final HealthCheckService healthCheckService) {
		this.projectHistoryRepository = projectHistoryRepository;
		this.healthCheckService = healthCheckService;
	}

	public List<ProjectSummary> getProjectSummaries(final List<Project> allProjects) {
		return allProjects
				.stream()
				.map(this::asProjectSummary)
				.collect(Collectors.toList());
	}

	private ProjectSummary asProjectSummary(final Project p) {
		final ProjectHistoryEntry historyEntry = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(p.getKey()).orElse(null);
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
		return builder.build();
	}

}
