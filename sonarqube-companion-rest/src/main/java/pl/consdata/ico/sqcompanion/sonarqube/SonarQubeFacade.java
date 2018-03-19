package pl.consdata.ico.sqcompanion.sonarqube;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gregorry
 */
public interface SonarQubeFacade {

    List<SonarQubeProject> getProjects(String serverId);

    List<SonarQubeMeasure> getProjectMeasureHistory(String serverId, String projectKey, LocalDate fromDate);

    List<SonarQubeIssue> getIssues(String serverId, IssueFilter filter);

}
