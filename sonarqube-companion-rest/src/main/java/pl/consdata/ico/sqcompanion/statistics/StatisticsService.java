package pl.consdata.ico.sqcompanion.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsService;

@Slf4j
@Service
public class StatisticsService {

    private final RepositoryService repositoryService;
    private UserStatisticsService userStatisticsService;

    public StatisticsService(final RepositoryService repositoryService,
                             final UserStatisticsService userStatisticsService) {
        this.repositoryService = repositoryService;
        this.userStatisticsService = userStatisticsService;
    }

    @Transactional
    public void syncStatistics() {
        repositoryService
                .getRootGroup()
                .accept(
                        gr -> gr.getProjects().forEach(this::syncProjectStatsAndCatch)
                );
    }

    private void syncProjectStatsAndCatch(final Project project) {
        try {
            syncProjectStats(project);
        } catch (final Exception exception) {
            log.error("Project statistics synchronization failed [Project={}]", project, exception);
        }
    }

    private void syncProjectStats(final Project project) {
        userStatisticsService.syncUserStats(project);
    }

}
