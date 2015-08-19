package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

public class SonarQubeMetricCollector {

    private final SonarQubeConnector sonarQubeConnector;

    public SonarQubeMetricCollector(final SonarQubeConnector sonarQubeConnector) {
        this.sonarQubeConnector = sonarQubeConnector;
    }

    public Map<String, List<Metric>> getMetrics(final Project project, final List<String> metricDefinitions) {
        final String url = String.format("/api/timemachine/index?format=json&metrics=%s&resource=%s",
                asMetricKeys(metricDefinitions),
                project.getKey());

        final ResponseEntity<SonarQubeTimeMachineResultsDto[]> serviceResult =
                sonarQubeConnector.getForEntity(url, SonarQubeTimeMachineResultsDto[].class);
        final SonarQubeTimeMachineResultsDto firstResult = serviceResult.getBody()[0];

        return mapAsResult(firstResult);
    }

    private Map<String, List<Metric>> mapAsResult(final SonarQubeTimeMachineResultsDto firstResult) {
        final Map<String, List<Metric>> result = new HashMap<>();

        Arrays.stream(firstResult.getCols()).forEach(col -> {
            result.put(col.getMetric(), new ArrayList<>());
        });
        Arrays.stream(firstResult.getCells()).forEach(cell -> {
            for (int idx = 0; idx < cell.getV().length; ++idx) {
                final String metricId = firstResult.getCols()[idx].getMetric();
                result.get(metricId).add(Metric.of(metricId, cell.getD(), cell.getV()[idx]));
            }
        });

        return result;
    }

    private String asMetricKeys(final List<String> metricDefinitions) {
        return metricDefinitions.stream().collect(Collectors.joining(","));
    }

}
