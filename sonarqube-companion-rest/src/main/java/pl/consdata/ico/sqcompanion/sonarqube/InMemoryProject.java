package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class InMemoryProject {

    private SonarQubeProject project;

    @Singular
    private List<SonarQubeIssue> issues;

    @Singular
    private List<SonarQubeMeasure> measures;

}
