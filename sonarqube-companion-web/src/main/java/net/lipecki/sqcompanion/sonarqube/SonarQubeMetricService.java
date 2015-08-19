package net.lipecki.sqcompanion.sonarqube;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sonarqube/metrics/v1/")
public class SonarQubeMetricService {

    private final SonarQubeMetricCollector sonarQubeMetricCollector;

    public SonarQubeMetricService(final SonarQubeMetricCollector sonarQubeMetricCollector) {
        this.sonarQubeMetricCollector = sonarQubeMetricCollector;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public Map<String, List<Metric>> hello() {
        return sonarQubeMetricCollector
                .getMetrics(
                        Project.of("pl.consdata.eximee.webforms:webforms"),
                        Arrays.asList("blocker_violations", "critical_violations")
                );
    }

}
