package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SonarQubeMetricCollector {

    private final SonarQubeConnector sonarQubeConnector;

    public SonarQubeMetricCollector(final SonarQubeConnector sonarQubeConnector) {
        this.sonarQubeConnector = sonarQubeConnector;
    }

    public void getMetrics(final Project project, final List<MetricDefinition> metricDefinitions) {
        final String fromDate = "2000-01-01T00:00:00+0200";

        final String url = String.format("/api/timemachine/index?format=json&metrics=%s&resource=%s&fromDateTime=%s",
                asMetricKeys(metricDefinitions), project.getKey(), fromDate);

        final ResponseEntity<SonarQubeTimeMachineResultsDto[]> result = sonarQubeConnector.getForEntity(url,
                SonarQubeTimeMachineResultsDto[].class);

        System.out.println(Arrays.toString(result.getBody()));
    }

    private String asMetricKeys(final List<MetricDefinition> metricDefinitions) {
        return metricDefinitions.stream().map(d -> d.getKey()).collect(Collectors.joining(","));
    }

}
