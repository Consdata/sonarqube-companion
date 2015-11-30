package net.lipecki.sqcompanion.sonarqube;

import com.google.common.collect.ObjectArrays;
import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesIssueResultDto;
import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesResultDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gregorry on 26.09.2015.
 */
public class SonarQubeService {

    private static final String PAGE_SIZE = "500";
    private static final String GET_ISSUES =
            "/api/issues/search?format=json&resolved=false&pageSize=" + PAGE_SIZE + "&componentRoots=%s";
    private static final String GET_ISSUES_PAGING
            = "/api/issues/search?format=json&resolved=false&pageSize=" + PAGE_SIZE + "&p=%d&componentRoots=%s";
    private static final String GET_HISTORY =
            "/api/timemachine/index?format=json&metrics=blocker_violations,critical_violations,major_violations" +
                    ",minor_violations,info_violations&resource=%s";

    private final SonarQubeConnector sonarQubeConnector;

    public SonarQubeService(final SonarQubeConnector sonarQubeConnector) {
        this.sonarQubeConnector = sonarQubeConnector;
    }

    public SonarQubeIssuesResultDto getIssues(final String key) {
        LOGGER.info("Request for issues [project={}]", key);
        final SonarQubeIssuesResultDto firstResult = sonarQubeConnector
                .getForEntity(String.format(GET_ISSUES, key), SonarQubeIssuesResultDto.class)
                .getBody();
        if (firstResult.getPaging().getPages() != null && firstResult.getPaging().getPages() > 1) {
            for (int page = 2; page <= firstResult.getPaging().getPages(); ++page) {
                LOGGER.info("Request for issues [project={}, page={}]", key, page);
                final SonarQubeIssuesResultDto partialResult = sonarQubeConnector
                        .getForEntity(String.format(GET_ISSUES_PAGING, page, key), SonarQubeIssuesResultDto.class)
                        .getBody();
                firstResult.setIssues(ObjectArrays.concat(firstResult.getIssues(), partialResult.getIssues(),
                        SonarQubeIssuesIssueResultDto.class));
            }
        }
        return firstResult;
    }

    public SonarQubeTimeMachineResultsDto getHistory(final String key) {
        LOGGER.info("Request for history [project={}]", key);
        final String serviceUrl = String.format(GET_HISTORY, key);
        return sonarQubeConnector.getForEntity(serviceUrl, SonarQubeTimeMachineResultsDto[].class).getBody()[0];
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarQubeService.class);

}
