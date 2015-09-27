package net.lipecki.sqcompanion.sonarqube;

import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesResultDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;

/**
 * Created by gregorry on 26.09.2015.
 */
public class SonarQubeService {

    public static final String GET_ISSUES = "/api/issues/search?format=json&resolved=false&pageSize=-1&componentRoots=%s";

    private static final String GET_HISTORY =
            "/api/timemachine/index?format=json&metrics=blocker_violations,critical_violations,major_violations" +
                    ",minor_violations,info_violations&resource=%s";

    private final SonarQubeConnector sonarQubeConnector;

    public SonarQubeService(final SonarQubeConnector sonarQubeConnector) {
        this.sonarQubeConnector = sonarQubeConnector;
    }

    public SonarQubeIssuesResultDto getIssues(final String key) {
        final String serviceUrl = String.format(GET_ISSUES, key);
        return sonarQubeConnector.getForEntity(serviceUrl, SonarQubeIssuesResultDto.class)
                .getBody();
    }

    public SonarQubeTimeMachineResultsDto getHistory(final String key) {
        final String serviceUrl = String.format(GET_HISTORY, key);
        return sonarQubeConnector.getForEntity(serviceUrl, SonarQubeTimeMachineResultsDto[].class).getBody()[0];
    }

}
