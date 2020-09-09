package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InMemoryProject {

    private SonarQubeProject project;

    private List<SonarQubeIssue> issues;

    private List<SonarQubeMeasure> measures;

}
