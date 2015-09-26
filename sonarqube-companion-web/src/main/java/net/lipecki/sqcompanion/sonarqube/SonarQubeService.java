package net.lipecki.sqcompanion.sonarqube;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesResultDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SonarQubeService {

    private final SonarQubeConnector sonarQubeConnector;

    private final Cache<String, SonarQubeIssuesResultDto> issuesCache;

    private final Cache<Object, SonarQubeTimeMachineResultsDto> historicalData;

    public SonarQubeService(final SonarQubeConnector sonarQubeConnector) {
        this.sonarQubeConnector = sonarQubeConnector;

        issuesCache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
        historicalData = CacheBuilder
                .newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build();
    }

    public SonarQubeTimeMachineResultsDto getTimeMachineResults(final String id, final List<String>
            metricDefinitions) {
        try {
            return historicalData.get(combinedProjectHistoricalDataCacheKey(id, metricDefinitions), new Callable<SonarQubeTimeMachineResultsDto>() {
                @Override
                public SonarQubeTimeMachineResultsDto call() throws Exception {
                    final String url = String.format("/api/timemachine/index?format=json&metrics=%s&resource=%s",
                            asMetricKeys(metricDefinitions),
                            id);

                    final ResponseEntity<SonarQubeTimeMachineResultsDto[]> serviceResult =
                            sonarQubeConnector.getForEntity(url, SonarQubeTimeMachineResultsDto[].class);

                    return serviceResult.getBody()[0];
                }
            });
        } catch (ExecutionException e) {
            // TODO: temporary throw exception
            throw new RuntimeException("Can't load project status");
        }
    }

    public SonarQubeIssuesResultDto getProjectIssues(final String projectKey, final String severity) {
        try {
            return issuesCache.get(combineProjectIssuesCacheKey(projectKey, severity), new Callable<SonarQubeIssuesResultDto>() {
                @Override
                public SonarQubeIssuesResultDto call() throws Exception {
                    final String url = String.format("/api/issues/search?format=json&resolved=false&pageSize=-1" +
                                    "&severities=%s" +
                                    "&componentRoots=%s",
                            severity, projectKey);
                    return sonarQubeConnector.getForEntity(url, SonarQubeIssuesResultDto.class).getBody();
                }
            });
        } catch (ExecutionException e) {
            // TODO: temporary throw exception
            throw new RuntimeException("Can't load project status");
        }
    }

    private String combinedProjectHistoricalDataCacheKey(final String id, final List<String> metricDefinitions) {
        return id + "#" + asMetricKeys(metricDefinitions);
    }

    private String combineProjectIssuesCacheKey(final String projectKey, final String severity) {
        return projectKey + "#" + severity;
    }

    private String asMetricKeys(final List<String> metricDefinitions) {
        return metricDefinitions.stream().collect(Collectors.joining(","));
    }

}
