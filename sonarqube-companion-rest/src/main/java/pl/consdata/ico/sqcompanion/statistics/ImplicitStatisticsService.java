package pl.consdata.ico.sqcompanion.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE;

@Slf4j
@Service
public class ImplicitStatisticsService {

    private final RepositoryService repositoryService;

    public ImplicitStatisticsService(final RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }


    public List<UserStatisticConfig> getImplicitUserStatisticsConfigFromWidgets() {
        return repositoryService
                .getRootGroup().getAllWidgetsByType(UserStatisticsDependency.class).stream().map(widget -> new UserStatisticConfig(LocalDate.parse(((UserStatisticsDependency) widget).getFrom(), ISO_DATE))).
                        collect(Collectors.toList());
    }
}
