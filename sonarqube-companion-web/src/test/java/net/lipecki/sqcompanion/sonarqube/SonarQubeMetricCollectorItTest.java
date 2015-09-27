package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.SonarQubeCompanion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SonarQubeCompanion.class)
public class SonarQubeMetricCollectorItTest {

    @Autowired
    private SonarQubeServiceOld collector;

    @Test
    public void call() {
//        final Map<String, List<SQMetric>> result =
//                collector.getTimeMachineResults(
//                        SQProject.of("pl.consdata.eximee.webforms:webforms"),
//                        Arrays.asList("critical_violations")
//                );
//
//        System.out.println(result);

        collector.getProjectIssues("pl.consdata.eximee.webforms:webforms", "CRITICAL");
    }

}
