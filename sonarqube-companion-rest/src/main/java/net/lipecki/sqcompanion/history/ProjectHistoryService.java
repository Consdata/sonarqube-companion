package net.lipecki.sqcompanion.history;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.repository.Project;
import net.lipecki.sqcompanion.repository.RepositoryService;
import net.lipecki.sqcompanion.sonarqube.SonarQubeFacade;
import net.lipecki.sqcompanion.sonarqube.SonarQubeMeasure;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collector;
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

			// get measures
			final List<SonarQubeMeasure> historicAnalyses = sonarQubeFacade.getProjectMeasureHistory(
					project.getServerId(),
					project.getKey(),
					null // TODO: use last existing in db date
			);

			// combine measures by dates and use latest for each day
			final Map<LocalDate, SonarQubeMeasure> combined = historicAnalyses
					.stream()
					.collect(Collectors.groupingBy(
							this::getLocalDate,
							Collectors.reducing(this::useLaterMeasure)
					))
					.entrySet()
					.stream()
					.collect(mapOptionalValuesToValues());

			// calculate historic entry for each past day, use previous available if non generated for analyzed day
			final List<ProjectHistoryEntry> history = new ArrayList<>();
			SonarQubeMeasure lastMeasure = combined.values().stream().sorted(Comparator.comparing(SonarQubeMeasure::getDate)).findFirst().get();
			for (LocalDate date = asLocalDate(lastMeasure.getDate()); date.isBefore(LocalDate.now()); date = date.plusDays(1)) {
				if (combined.containsKey(date)) {
					lastMeasure = combined.get(date);
				}
				history.add(mapMeasureToHistoryEntry(date, lastMeasure));
			}

			log.debug("Project violations history builded [history={}]", history);

			// TODO: store in db
		} catch (final Exception exception) {
			log.error("Project history synchronization failed [project={}]", project, exception);
		}
	}

	private Collector<Map.Entry<LocalDate, Optional<SonarQubeMeasure>>, ?, Map<LocalDate, SonarQubeMeasure>> mapOptionalValuesToValues() {
		return Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get());
	}

	private SonarQubeMeasure useLaterMeasure(final SonarQubeMeasure a, final SonarQubeMeasure b) {
		return a.getDate().after(b.getDate()) ? a : b;
	}

	private LocalDate getLocalDate(final SonarQubeMeasure measure) {
		return asLocalDate(measure.getDate());
	}

	private LocalDate asLocalDate(final Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private ProjectHistoryEntry mapMeasureToHistoryEntry(final LocalDate date, final SonarQubeMeasure measure) {
		return ProjectHistoryEntry
				.builder()
				.blockers(measure.getBlockers())
				.criticals(measure.getCriticals())
				.majors(measure.getMajors())
				.minors(measure.getMinors())
				.infos(measure.getInfos())
				.date(date)
				.build();
	}

}
