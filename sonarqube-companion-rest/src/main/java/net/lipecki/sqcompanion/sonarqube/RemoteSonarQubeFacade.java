package net.lipecki.sqcompanion.sonarqube;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.sonarqube.sqapi.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@Service
@Slf4j
public class RemoteSonarQubeFacade implements SonarQubeFacade {

	public static final String ALL_VIOLATION_METRICS = "blocker_violations,critical_violations,major_violations,minor_violations,info_violations";
	private final SonarQubeConnector sonarQubeConnector;

	public RemoteSonarQubeFacade(final SonarQubeConnector sonarQubeConnector) {
		this.sonarQubeConnector = sonarQubeConnector;
	}

	public List<SonarQubeProject> getProjects(final String serverId) {
		return sonarQubeConnector.getForPaginatedList(
				serverId,
				"api/projects/search",
				SQProjectsSearchResponse.class,
				SQProjectsSearchResponse::getComponents
		).map(this::mapComponentToProject
		).collect(Collectors.toList());
	}

	public List<SonarQubeIssue> getIssues(final String serverId, final String projectKey) {
		return sonarQubeConnector.getForPaginatedList(
				serverId,
				"api/issues/search?projectKeys=" + projectKey,
				SQIssuesSearchResponse.class,
				SQIssuesSearchResponse::getIssues
		).map(this::mapSqIssueToIssue
		).collect(Collectors.toList());
	}

	public List<SonarQubeMessure> getProjectMessureHistory(final String serverId, final String projectKey, final Date fromDate) {
		final StringBuilder serviceUri = new StringBuilder("api/measures/search_history")
				.append("?component=" + projectKey)
				.append("&metrics=" + ALL_VIOLATION_METRICS);
		if (fromDate != null) {
			serviceUri.append("&from=" + new SimpleDateFormat("yyyy-MM-dd").format(fromDate));
		}

		final List<SQMessure> messures = sonarQubeConnector.getForPaginatedList(
				serverId,
				serviceUri.toString(),
				SQMessuresSearchHistoryResponse.class,
				SQMessuresSearchHistoryResponse::getMeasures
		).collect(Collectors.toList());

		final Map<Date, SonarQubeMessure> messureDateIndex = new HashMap<>();
		for (final SQMessure messure : messures) {
			for (final SQMessureHistory historyEntry : messure.getHistory()) {
				messureDateIndex.putIfAbsent(
						historyEntry.getDate(),
						SonarQubeMessure.builder().date(historyEntry.getDate()).build()
				);
				final SonarQubeMessure dateMessure = messureDateIndex.get(historyEntry.getDate());
				dateMessure.putMetric(messure.getMetric(), historyEntry.getValue());
			}
		}

		return messureDateIndex
				.values()
				.stream()
				.sorted(Comparator.comparing(SonarQubeMessure::getDate))
				.collect(Collectors.toList());
	}

	private SonarQubeProject mapComponentToProject(final SQComponent component) {
		return SonarQubeProject
				.builder()
				.key(component.getKey())
				.name(component.getName())
				.build();
	}

	private SonarQubeIssue mapSqIssueToIssue(final SQIssue sqIssue) {
		return SonarQubeIssue
				.builder()
				.key(sqIssue.getKey())
				.creationDate(sqIssue.getCreationDate())
				.message(sqIssue.getMessage())
				.build();
	}

}
