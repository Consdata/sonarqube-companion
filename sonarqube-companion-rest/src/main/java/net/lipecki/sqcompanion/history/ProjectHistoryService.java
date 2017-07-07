package net.lipecki.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.repository.Project;
import net.lipecki.sqcompanion.repository.RepositoryService;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import org.springframework.stereotype.Service;

/**
 * Możliwości rozwoju:
 * - odświeżanie automatyczne przez cron
 */
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

    public void syncProjectsHistory() {
        repositoryService.getRootGroup().accept(gr -> gr.getProjects().stream().forEach(this::synProjectHistory));
    }

    private void synProjectHistory(final Project project) {
        log.debug("Syncing project [project={}]", project);
        sonarQubeFacade.getIssues(project.getServerId(), project.getKey());
    }

}
