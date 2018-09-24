package pl.consdata.ico.sqcompanion.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Project;

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
        final Optional<ProjectHistoryEntryEntity> currentState = projectHistoryRepository.findFirstByProjectKeyOrderByDateDesc(project.getKey());
        if (currentState.isPresent()) {
            final ProjectHistoryEntryEntity entry = currentState.get();
            if (entry.getBlockers() > 0) {
                return HealthStatus.UNHEALTHY;
            } else if (entry.getCriticals() > 0) {
                return HealthStatus.WARNING;
            } else {
                return HealthStatus.HEALTHY;
            }
        } else {
            return HealthStatus.UNKNOWN;
        }
    }

}
