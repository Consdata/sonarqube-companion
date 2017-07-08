package net.lipecki.sqcompanion.sonarqube;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.ServerDefinition;
import net.lipecki.sqcompanion.sonarqube.sqapi.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gregorry
 */
@Service
@Slf4j
public class RemoteSonarQubeFacade implements SonarQubeFacade {

	public static final int DEFAULT_PAGE_SIZE = 100;
	// SonarQube WEB Api uses 1-based page indexes
	public static final int FIRST_PAGE = 1;
	public static final String PAGING_TEMPLATE = "p=%d&ps=%d";
	public static final String SERVER_WITH_URI_TEMPLATE = "%s%s";
	public static final String ALL_VIOLATION_METRICS = " blocker_violations,critical_violations,major_violations,minor_violations,info_violations";
	private final AppConfig config;
	private final RestTemplate restTemplate;

	public RemoteSonarQubeFacade(final AppConfig config, final RestTemplate restTemplate) {
		this.config = config;
		this.restTemplate = restTemplate;
	}

	public List<SonarQubeProject> getProjects(final String serverId) {
		return getForPaginatedList(
				serverId,
				"api/projects/search",
				SQProjectsSearchResponse.class,
				SQProjectsSearchResponse::getComponents
		).map(this::mapComponentToProject
		).collect(Collectors.toList());
	}

	public List<SonarQubeIssue> getIssues(final String serverId, final String projectKey) {
		return getForPaginatedList(
				serverId,
				"api/issues/search?projectKeys=" + projectKey,
				SQIssuesSearchResponse.class,
				SQIssuesSearchResponse::getIssues
		).map(this::mapSqIssueToIssue
		).collect(Collectors.toList());
	}

	public List<SonarQubeMessure> getProjectMessureHistory(final String serverId, final String projectKey) {
		return getProjectMessureHistory(serverId, projectKey, null);
	}

	public List<SonarQubeMessure> getProjectMessureHistory(final String serverId, final String projectKey, final Date fromDate) {
		final StringBuilder serviceUri = new StringBuilder("api/measures/search_history")
				.append("?component=" + projectKey)
				.append("&metrics=" + ALL_VIOLATION_METRICS);
		if (fromDate != null) {
			serviceUri.append("&from=" + new SimpleDateFormat("yyyy-MM-dd").format(fromDate));
		}

		final List<SQMessure> messures = getForPaginatedList(
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

	private <T extends SQPaginatedResponse, R> Stream<R> getForPaginatedList(
			final String serverId,
			final String uri,
			final Class<T> responseClass,
			final Function<T, List<R>> dataExtractor) {
		final List<R> result = new ArrayList<>();

		final ServerDefinition server = getServerDefinition(serverId);
		restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("admin", "admin"));

		T lastResponse = null;
		do {
			int pageIdx = lastResponse != null ? lastResponse.getNextPage() : FIRST_PAGE;
			lastResponse = restTemplate.getForObject(
					String.format(
							SERVER_WITH_URI_TEMPLATE + (uri.contains("?") ? "&" : "?") + PAGING_TEMPLATE,
							server.getUrl(),
							uri,
							pageIdx,
							DEFAULT_PAGE_SIZE
					),
					responseClass
			);
			result.addAll(dataExtractor.apply(lastResponse));
		} while (lastResponse.hasMorePages());

		return result.stream();
	}

	private ServerDefinition getServerDefinition(final String serverId) {
		return config.getServers()
				.stream()
				.filter(s -> s.getId().equals(serverId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find server for id: " + serverId));
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
