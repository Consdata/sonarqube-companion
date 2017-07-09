package net.lipecki.sqcompanion.project;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.health.HealthCheckService;
import net.lipecki.sqcompanion.history.ProjectHistoryEntry;
import net.lipecki.sqcompanion.history.ProjectHistoryRepository;
import net.lipecki.sqcompanion.repository.Project;
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
				.health(healthCheckService.checkHealth(p));
		if (historyEntry != null) {
			builder.violations(
					ProjectViolations
							.builder()
							.blockers(historyEntry != null ? historyEntry.getBlockers() : null)
							.criticals(historyEntry != null ? historyEntry.getCriticals() : null)
							.majors(historyEntry != null ? historyEntry.getMajors() : null)
							.minors(historyEntry != null ? historyEntry.getMinors() : null)
							.infos(historyEntry != null ? historyEntry.getInfos() : null)
							.build()
			);
		}
		return builder.build();
	}

}
