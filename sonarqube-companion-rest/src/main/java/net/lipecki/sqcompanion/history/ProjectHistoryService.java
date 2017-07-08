package net.lipecki.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.repository.Project;
import net.lipecki.sqcompanion.repository.RepositoryService;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import net.lipecki.sqcompanion.sonarqube.SonarQubeMessure;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        try {
            log.debug("Syncing project [project={}]", project);
            final List<SonarQubeMessure> messures = sonarQubeFacade.getProjectMessureHistory(
                    project.getServerId(),
                    project.getKey()
            );

            // aggregate to days
            // fill missing days with last value
            // sync values to db

            final Map<LocalDate, List<SonarQubeMessure>> aggregated = messures
                    .stream()
                    .sorted(Comparator.comparing(SonarQubeMessure::getDate))
                    .collect(Collectors.groupingBy(e -> asLocalDate(e.getDate())));
        } catch (final Exception exception) {
            log.error("Project history synchronization failed [project={}]", project, exception);
        }
    }

    private LocalDate asLocalDate(final Date date) {
        return LocalDate.ofEpochDay(date.getTime());
    }

}
