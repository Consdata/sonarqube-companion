package net.lipecki.sqcompanion.sonarqube;

import java.util.Date;
import java.util.List;

/**
 * @author gregorry
 */
public interface SonarQubeFacade {

	List<SonarQubeProject> getProjects(String serverId);

	List<SonarQubeIssue> getIssues(String serverId, String projectKey);

	List<SonarQubeMeasure> getProjectMeasureHistory(String serverId, String projectKey, Date fromDate);

}
