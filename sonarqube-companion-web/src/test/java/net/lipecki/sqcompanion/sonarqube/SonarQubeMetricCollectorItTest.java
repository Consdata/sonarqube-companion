package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.SonarQubeCompanion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SonarQubeCompanion.class)
public class SonarQubeMetricCollectorItTest {

    @Autowired
    private SonarQubeMetricCollector collector;

    @Test
    public void call() {
        final Map<String, List<Metric>> result =
                collector.getMetrics(
                        Project.of("pl.consdata.eximee.webforms:webforms"),
                        Arrays.asList("critical_violations")
                );

        System.out.println(result);
    }

}
