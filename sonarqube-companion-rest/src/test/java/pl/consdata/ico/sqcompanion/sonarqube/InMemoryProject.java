package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class InMemoryProject {

    private SonarQubeProject project;

    private List<SonarQubeIssue> issues;

    private List<SonarQubeMeasure> measures;

}
