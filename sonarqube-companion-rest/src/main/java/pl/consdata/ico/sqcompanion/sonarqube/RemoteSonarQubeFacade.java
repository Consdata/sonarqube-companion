package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilterFacet;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public List<SonarQubeProject> projects(final String serverId) {
        return sonarQubeConnector
                .getForPaginatedList(
                        serverId,
                        "api/components/search_projects",
                        SQComponentSearchResponse.class,
                        SQComponentSearchResponse::getComponents
                )
                .map(this::componentToProject)
                .collect(Collectors.toList());
    }

    @Override
    public List<SonarQubeIssue> issues(final String serverId, final IssueFilter filter) {
        return sonarQubeConnector
                .getForPaginatedList(
                        serverId,
                        "api/issues/search" + filter.query().map(q -> "?" + q).orElse(""),
                        SQIssuesSearchResponse.class,
                        SQIssuesSearchResponse::getIssues
                )
                .map(this::sqIssueToIssue)
                .collect(Collectors.toList());
    }

    @Override
    public SonarQubeIssuesFacets issuesFacet(String serverId, IssueFilter filter) {
        if (filter.getFacets().isEmpty()) {
            throw new IllegalArgumentException("Facet filter required for facet query");
        }
        filter.setLimit(1);

        final Map<IssueFilterFacet, SonarQubeIssuesFacet> facets = new HashMap<>();
        sonarQubeConnector
                .getForPaginatedList(
                        serverId,
                        "api/issues/search" + filter.query().map(q -> "?" + q).orElse(""),
                        SQIssuesSearchResponse.class,
                        SQIssuesSearchResponse::getFacets
                )
                .forEach(facet -> facets.put(
                        IssueFilterFacet.ofValue(facet.getProperty()),
                        SonarQubeIssuesFacet.builder()
                                .values(facet.getValues())
                                .build()
                ));

        return SonarQubeIssuesFacets.builder().facets(facets).build();
    }

    @Override
    public List<SonarQubeMeasure> projectMeasureHistory(final String serverId, final String projectKey, final LocalDate fromDate) {
        final StringBuilder serviceUri = new StringBuilder("api/measures/search_history")
                .append("?component=" + projectKey)
                .append("&metrics=" + ALL_VIOLATION_METRICS);
        if (fromDate != null) {
            serviceUri.append("&from=" + fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        final List<SQMeasure> measures = sonarQubeConnector.getForPaginatedList(
                serverId,
                serviceUri.toString(),
                SQMeasuresSearchHistoryResponse.class,
                SQMeasuresSearchHistoryResponse::getMeasures
        ).collect(Collectors.toList());

        final Map<Date, SonarQubeMeasure> measureDateIndex = new HashMap<>();
        for (final SQMeasure measure : measures) {
            for (final SQMeasureHistory historyEntry : measure.getHistory()) {
                measureDateIndex.putIfAbsent(
                        historyEntry.getDate(),
                        SonarQubeMeasure.builder().date(historyEntry.getDate()).build()
                );
                final SonarQubeMeasure dateMeasure = measureDateIndex.get(historyEntry.getDate());
                dateMeasure.putMetric(measure.getMetric(), historyEntry.getValue());
            }
        }

        return measureDateIndex
                .values()
                .stream()
                .sorted(Comparator.comparing(SonarQubeMeasure::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<SonarQubeUser> users(String serverId) {
        return sonarQubeConnector
                .getForPaginatedList(
                        serverId,
                        "api/users/search",
                        SQUsersSearchResponse.class,
                        SQUsersSearchResponse::getUsers
                )
                .map(this::sqUserToUser)
                .filter(u -> StringUtils.isNotBlank(u.getUserId()))
                .collect(Collectors.toList());
    }

    private SonarQubeUser sqUserToUser(SQUser sqUser) {
        return SonarQubeUser.builder()
                .userId(sqUser.getEmail())
                .build();
    }

    private SonarQubeProject componentToProject(final SQComponent component) {
        return SonarQubeProject
                .builder()
                .key(component.getKey())
                .name(component.getName())
                .build();
    }

    private SonarQubeIssue sqIssueToIssue(final SQIssue sqIssue) {
        return SonarQubeIssue
                .builder()
                .key(sqIssue.getKey())
                .creationDate(sqIssue.getCreationDate())
                .message(sqIssue.getMessage())
                .severity(asSeveriyt(sqIssue))
                .author(sqIssue.getAuthor())
                .updateDate(sqIssue.getUpdateDate())
                .build();
    }

    private SonarQubeIssueSeverity asSeveriyt(SQIssue sqIssue) {
        return SonarQubeIssueSeverity.valueOf(sqIssue.getSeverity());
    }

}
