package pl.consdata.ico.sqcompanion.sonarqube;

import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author gregorry
 */
public interface SonarQubeFacade {

    List<SonarQubeProject> projects(String serverId);

    List<SonarQubeIssue> issues(String serverId, IssueFilter filter);

    SonarQubeIssuesFacets issuesFacet(String serverId, IssueFilter filter);

    List<SonarQubeMeasure> projectMeasureHistory(String serverId, String projectKey, LocalDate fromDate);

    List<SonarQubeUser> users(String serverId);
}
