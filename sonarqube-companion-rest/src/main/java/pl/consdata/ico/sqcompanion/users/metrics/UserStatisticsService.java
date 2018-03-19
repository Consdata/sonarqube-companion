package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.history.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.history.ViolationsHistory;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.RemoteSonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQUser;
import pl.consdata.ico.sqcompanion.users.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserStatisticsService {
    private RepositoryService repositoryService;
    private RemoteSonarQubeFacade remoteSonarQubeFacade;
    private UserService userService;

    public UserStatisticsService(RepositoryService repositoryService,
                                 RemoteSonarQubeFacade remoteSonarQubeFacade,
                                 UserService userService) {
        this.repositoryService = repositoryService;
        this.remoteSonarQubeFacade = remoteSonarQubeFacade;
        this.userService = userService;
    }

    private List<SQUser> getUsersForProject(final Project project) {
        return remoteSonarQubeFacade.getUsers(project.getServerId(), false);
    }

    private List<SonarQubeIssue> getIssuesForUser(final SQUser user, final Project project) {
        return remoteSonarQubeFacade.getIssues(project.getServerId(), IssueFilter.builder().author(user.getEmail()).projectKey(project.getKey()).status("OPEN").build());
    }

    private void syncUserStatusPerProject(final Project project, SQUser user) {

    }

    public void syncUserStats(final Project project) {
        List<SQUser> users = getUsersForProject(project);
        users.forEach(user -> syncUserStatusPerProject(project, user));
    }

    @Cacheable(value = Caches.PROJECT_USER_STATISTICS_CACHE, sync = true, key = "#project.uuid + #daysLimit")
    public ViolationsHistory getProjectUserStatistics(final Project project, Optional<Integer> daysLimit) {

    }

    @Cacheable(value = Caches.PROJECT_USER_STATISTICS_DIFF_CACHE, sync = true, key = "#project.uuid + #fromDate + #toDate")
    public GroupViolationsHistoryDiff getProjectUserStatisticsDiff(final Project project, final LocalDate fromDate, final LocalDate toDate) {

    }

    @Cacheable(value = Caches.GROUP_USER_STATISTICS_CACHE, sync = true, key = "#group.uuid + #daysLimit")
    public ViolationsHistory getGroupUserStatistics(final Group group, Optional<Integer> daysLimit) {

    }

    @Cacheable(value = Caches.GROUP_USER_STATISTICS_DIFF_CACHE, sync = true, key = "#group.uuid + #fromDate + #toDate")
    public GroupViolationsHistoryDiff getGroupUserStatisticsDiff(final Group group, final LocalDate fromDate, final LocalDate toDate) {

    }
}
