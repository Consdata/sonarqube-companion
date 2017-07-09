package net.lipecki.sqcompanion.health;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.history.ProjectHistoryEntry;
import net.lipecki.sqcompanion.history.ProjectHistoryRepository;
import net.lipecki.sqcompanion.repository.Project;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author gregorry
 */
@Slf4j
@Service
public class IssueCountHealthVoter implements HealthVoter {

	private final ProjectHistoryRepository projectHistoryRepository;

	public IssueCountHealthVoter(final ProjectHistoryRepository projectHistoryRepository) {
		this.projectHistoryRepository = projectHistoryRepository;
	}

	@Override
	public HealthStatus checkHealth(final Project project) {
		final Optional<ProjectHistoryEntry> currentState = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(project.getKey());
		if (currentState.isPresent()){
			final ProjectHistoryEntry entry = currentState.get();
			if (entry.getBlockers() > 0) {
				return HealthStatus.UNHEALTHY;
			} else if (entry.getCriticals() > 0) {
				return HealthStatus.WARNING;
			} else {
				return HealthStatus.HEALTHY;
			}
		} else {
			return null;
		}
	}

}
