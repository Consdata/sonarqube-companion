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
public class ImplicitStatisticeService {

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
        return repositoryService.getRootGroup().getAllStatisticConfigs(UserStatisticConfig.TYPE);
    }

    private List<UserStatisticConfig> getImplicitUserStatisticsConfigFromWidgets() {
        repositoryService
                .getRootGroup().getAllGroups().stream().map(
                group -> group.getWidgets().stream().filter()
        );
    }

    private void syncUserProjectStats(final Project project, List<StatisticConfig> configs) {
        if (!configs.isEmpty()) {
            configs.addAll(getImplicitUserStatisticsConfigFromWidgets());
            configs.sort(Comparator.comparing(a -> ((UserStatisticConfig) a).getFrom()));
            userStatisticsService.syncUserStats(project, (UserStatisticConfig) configs.get(0));
        }
    }

    private void syncProjectStats(final Project project) {
        syncUserProjectStats(project, getUserStatisticConfigs());
    }

}
