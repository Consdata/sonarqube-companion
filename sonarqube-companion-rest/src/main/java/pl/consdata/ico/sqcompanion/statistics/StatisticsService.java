package pl.consdata.ico.sqcompanion.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsService;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class StatisticsService {

    private final RepositoryService repositoryService;
    private UserStatisticsService userStatisticsService;
    private ImplicitStatisticsService implicitStatisticsService;
    private List<StatisticConfig> userStatisticConfigs;

    public StatisticsService(final RepositoryService repositoryService,
                             final UserStatisticsService userStatisticsService,
                             final ImplicitStatisticsService implicitStatisticsService) {
        this.repositoryService = repositoryService;
        this.userStatisticsService = userStatisticsService;
        this.implicitStatisticsService = implicitStatisticsService;
        this.userStatisticConfigs = getUserStatisticConfigs();
    }

    @Transactional
    public void syncStatistics() {
        repositoryService
                .getRootGroup().getAllProjects().forEach(this::syncProjectStatsAndCatch);
    }


    private void syncProjectStatsAndCatch(final Project project) {
        try {
            syncProjectStats(project);
        } catch (final Exception exception) {
            log.error("Project statistics synchronization failed [Project={}]", project, exception);
        }
    }

    private List<StatisticConfig> getUserStatisticConfigs() {
        List<StatisticConfig> configs = repositoryService.getRootGroup().getAllStatisticConfigsByType(UserStatisticConfig.TYPE);
        configs.addAll(implicitStatisticsService.getImplicitUserStatisticsConfigFromWidgets());
        return configs;
    }

    private void syncUserProjectStats(final Project project, List<StatisticConfig> configs) {
        if (!configs.isEmpty()) {
            configs.sort(Comparator.comparing(a -> ((UserStatisticConfig) a).getFrom()));
            userStatisticsService.syncUserStats(project, (UserStatisticConfig) configs.get(0));
        }
    }

    private void syncProjectStats(final Project project) {
        syncUserProjectStats(project, userStatisticConfigs);
    }

}
