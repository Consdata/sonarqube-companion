package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.SonarQubeCompanion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SonarQubeCompanion.class)
public class SonarQubeMetricCollectorItTest {

    @Autowired
    private SonarQubeMetricCollector collector;

    @Test
    public void call() {
        collector.getMetrics(Project.of("pl.consdata.eximee.webforms:webforms"), Arrays.asList(MetricDefinition.of("critical_violations")));
    }

}
