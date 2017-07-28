package pl.consdata.ico.sqcompanion.sonarqube;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gregorry
 */
public interface SonarQubeFacade {

	List<SonarQubeProject> getProjects(String serverId);

	List<SonarQubeIssue> getIssues(String serverId, String projectKey);

	List<SonarQubeMeasure> getProjectMeasureHistory(String serverId, String projectKey, LocalDate fromDate);

}
