package net.lipecki.sqcompanion.health;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.project.ProjectSummary;
import net.lipecki.sqcompanion.repository.Project;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class HealthCheckService {

	private final List<HealthVoter> voters;

	public HealthCheckService(final List<HealthVoter> voters) {
		this.voters = voters;
	}

	public HealthStatus getCombinedProjectsHealth(final List<ProjectSummary> projectSummaries) {
		return projectSummaries
				.stream()
				.map(ProjectSummary::getHealth)
				.reduce((a, b) -> a.getPriority() > b.getPriority() ? a : b)
				.orElse(HealthStatus.UNKNOWN);
	}

	public HealthStatus checkHealth(final Project project) {
		HealthStatus status = HealthStatus.UNKNOWN;
		for (final HealthVoter voter : voters) {
			final HealthStatus projectStatus = voter.checkHealth(project);
			if (projectStatus.getPriority() > status.getPriority()) {
				status = projectStatus;
			}
		}
		return status;
	}

}
