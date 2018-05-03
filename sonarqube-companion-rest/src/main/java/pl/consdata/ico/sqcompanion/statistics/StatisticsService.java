package pl.consdata.ico.sqcompanion.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        gr -> gr.getProjects().forEach(project -> this.syncProjectStatsAndCatch(project, gr.getStatistics()))
                );
    }

    private void syncProjectStatsAndCatch(final Project project, List<StatisticConfig> config) {
        try {
            if (config != null) {
                syncProjectStats(project, asMap(config));
            }
        } catch (final Exception exception) {
            log.error("Project statistics synchronization failed [Project={}]", project, exception);
        }
    }

    private Map<String, StatisticConfig> asMap(List<StatisticConfig> configs) {
        Map<String, StatisticConfig> output = new HashMap<>();
        for (StatisticConfig config : configs) {
            if (config instanceof UserStatisticConfig) {
                output.putIfAbsent(UserStatisticConfig.TYPE, config);
            }
        }
        return output;
    }

    private void syncProjectStats(final Project project, Map<String, StatisticConfig> configMap) {
        if (configMap.containsKey(UserStatisticConfig.TYPE)) {
            userStatisticsService.syncUserStats(project, (UserStatisticConfig) configMap.get(UserStatisticConfig.TYPE));
        }
    }

}
