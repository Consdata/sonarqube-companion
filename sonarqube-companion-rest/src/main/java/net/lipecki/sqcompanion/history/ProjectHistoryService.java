package net.lipecki.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.repository.Project;
import net.lipecki.sqcompanion.repository.RepositoryService;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import net.lipecki.sqcompanion.sonarqube.SonarQubeMessure;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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

			// get messures
			final List<SonarQubeMessure> historicAnalyses = sonarQubeFacade.getProjectMessureHistory(
					project.getServerId(),
					project.getKey(),
					null // TODO: use last existing in db date
			);

			// aggregate messures if multiple for one day
			final Map<LocalDate, List<SonarQubeMessure>> aggregated = historicAnalyses
					.stream()
					.sorted(Comparator.comparing(SonarQubeMessure::getDate))
					.collect(Collectors.groupingBy(e -> asLocalDate(e.getDate())));

			// combine multiple messures to one messure for each day
			final Map<LocalDate, SonarQubeMessure> combined = new HashMap<>();
			for (final Map.Entry<LocalDate, List<SonarQubeMessure>> entry : aggregated.entrySet()) {
				combined.put(
						entry.getKey(),
						entry.getValue()
								.stream()
								.sorted(Comparator.comparing(SonarQubeMessure::getDate))
								.findFirst()
								.get()
				);
			}

			// calculate historic entry for each past day
			final List<ProjectHistoryEntry> history = new ArrayList<>();
			SonarQubeMessure lastMessure = combined.values().stream().sorted(Comparator.comparing(SonarQubeMessure::getDate)).findFirst().get();
			for (LocalDate date = asLocalDate(lastMessure.getDate()); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
				if (combined.containsKey(date)) {
					lastMessure = combined.get(date);
				}
				history.add(mapMessureToHistoryEntry(date, lastMessure));
			}

			log.debug("Project violations history builded [history={}]", history);

			// TODO: store in db
		} catch (final Exception exception) {
			log.error("Project history synchronization failed [project={}]", project, exception);
		}
	}

	private LocalDate asLocalDate(final Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private ProjectHistoryEntry mapMessureToHistoryEntry(final LocalDate date, final SonarQubeMessure messure) {
		return ProjectHistoryEntry
				.builder()
				.blockers(messure.getBlockers())
				.criticals(messure.getCriticals())
				.majors(messure.getMajors())
				.minors(messure.getMinors())
				.infos(messure.getInfos())
				.date(date)
				.build();
	}

}
