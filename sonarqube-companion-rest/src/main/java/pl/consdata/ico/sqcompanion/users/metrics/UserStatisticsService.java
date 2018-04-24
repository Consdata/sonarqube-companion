package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.sonarqube.*;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQUser;
import pl.consdata.ico.sqcompanion.statistics.UserStatisticConfig;
import pl.consdata.ico.sqcompanion.statistics.UserStatisticsRepository;
import pl.consdata.ico.sqcompanion.users.UserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserStatisticsService {
    private RepositoryService repositoryService;
    private RemoteSonarQubeFacade remoteSonarQubeFacade;
    private UserService userService;
    private UserStatisticsRepository userStatisticsRepository;

    public UserStatisticsService(RepositoryService repositoryService,
                                 RemoteSonarQubeFacade remoteSonarQubeFacade,
                                 UserService userService,
                                 UserStatisticsRepository userStatisticsRepository) {
        this.repositoryService = repositoryService;
        this.remoteSonarQubeFacade = remoteSonarQubeFacade;
        this.userService = userService;
        this.userStatisticsRepository = userStatisticsRepository;
    }

    private List<SQUser> getUsersForProject(final Project project) {
        return remoteSonarQubeFacade.getUsers(project.getServerId(), false);
    }

    private List<SonarQubeIssue> getIssuesForUsers(final List<SQUser> users, final Project project) {
        IssueFilter.IssueFilterBuilder builder = IssueFilter.builder();
        users.forEach(user -> builder.author(user.getEmail()));
        return remoteSonarQubeFacade.getIssues(project.getServerId(), builder.projectKey(project.getKey()).status(SonarQubeIssueStatus.OPEN.name()).build());

    }

    private void syncUsersStatsPerProject(final Project project, List<SQUser> users, UserStatisticConfig config) {
        List<SonarQubeIssue> issues = getIssuesForUsers(users, project);
        Map<String, List<SonarQubeIssue>> issueMap = mapIssuesToAuthorIssueMap(issues, users);
        Set<UserStatisticsEntryEntity> entries = users.stream().map(user -> getUserStatisticsEntriesPerDay(project, user, issueMap.get(user.getEmail()), config.getFrom())).flatMap(Set::stream).collect(Collectors.toSet());
        userStatisticsRepository.saveAll(entries);
    }

    private Set<UserStatisticsEntryEntity> getUserStatisticsEntriesPerDay(final Project project, final SQUser user, final List<SonarQubeIssue> issues, LocalDate from) {
        LocalDate currentDate = from;
        Set<UserStatisticsEntryEntity> entries = new HashSet<>();
        while (currentDate.isBefore(LocalDate.now().plusDays(1))) {
            List<SonarQubeIssue> dailyIssues = getIssuesBetween(issues, currentDate, currentDate.plusDays(1));
            entries.add(createUserStatisticsEntry(project, user, dailyIssues, currentDate, currentDate.plusDays(1)));
            currentDate = currentDate.plusDays(1);
        }

        return entries;
    }

    private List<SonarQubeIssue> getIssuesBetween(final List<SonarQubeIssue> issues, final LocalDate from, final LocalDate to) {
        return issues.stream()
                .filter(issue -> isAfterOrSame(issue.getCreationDate(), from))
                .filter(issue -> asLocalDate(issue.getCreationDate()).isBefore(to)).collect(Collectors.toList());
    }

    private UserStatisticsEntryEntity createUserStatisticsEntry(final Project project, final SQUser user, final List<SonarQubeIssue> issues, LocalDate from, LocalDate to) {
        String id = UserStatisticsEntryEntity.combineId(project.getServerId(), project.getKey(), user.getEmail(), from, to);
        return UserStatisticsEntryEntity.builder()
                .id(id)
                .user(user.getEmail())
                .projectKey(project.getKey())
                .serverId(project.getServerId())
                .begin(from)
                .end(to)
                .blockers(countBySeverity(issues, SonarQubeIssueSeverity.BLOCKER, from, to))
                .criticals(countBySeverity(issues, SonarQubeIssueSeverity.CRITICAL, from, to))
                .majors(countBySeverity(issues, SonarQubeIssueSeverity.MAJOR, from, to))
                .minors(countBySeverity(issues, SonarQubeIssueSeverity.MINOR, from, to))
                .infos(countBySeverity(issues, SonarQubeIssueSeverity.INFO, from, to))
                .build();
    }

    private long countBySeverity(List<SonarQubeIssue> issues, SonarQubeIssueSeverity severity, LocalDate from, LocalDate to) {
        return issues.stream().filter(issue -> issue.getSeverity().equals(severity))
                .filter(issue -> isAfterOrSame(issue.getCreationDate(), from))
                .filter(issue -> asLocalDate(issue.getCreationDate()).isBefore(to)).count();
    }

    private LocalDate asLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private boolean isAfterOrSame(Date date, LocalDate limit) {
        LocalDate localDate = asLocalDate(date);
        return localDate.isAfter(limit) || localDate.isEqual(limit);
    }

    private Map<String, List<SonarQubeIssue>> mapIssuesToAuthorIssueMap(List<SonarQubeIssue> issues, List<SQUser> users) {
        Map<String, List<SonarQubeIssue>> output = new HashMap<>();
        users.forEach(user -> output.put(user.getEmail(), new ArrayList<>()));
        issues.forEach(issue -> output.get(issue.getAuthor()).add(issue));
        return output;
    }

    public void syncUserStats(final Project project, final UserStatisticConfig config) {
        log.info("Syncing users statistics for project [project={}]", project);
        List<SQUser> users = getUsers(project);
        syncUsersStatsPerProject(project, users, config);
    }

    @Cacheable(value = Caches.USERS_CACHE, sync = true, key = "#project.key")
    public List<SQUser> getUsers(final Project project) {
        return getUsersForProject(project);
    }

    @Cacheable(value = Caches.PROJECT_USER_STATISTICS_CACHE, sync = true, key = "#project.getId() + #daysLimit")
    public void getProjectUserStatistics(final Project project, Optional<Integer> daysLimit) {

    }

    @Cacheable(value = Caches.PROJECT_USER_STATISTICS_DIFF_CACHE, sync = true, key = "#project.getId() + #fromDate + #toDate")
    public List<UserStatisticsEntryEntity> getProjectUserStatisticsDiff(final Project project, final LocalDate fromDate, final LocalDate toDate) {
        return userStatisticsRepository.findAllByProjectKeyAndBeginIsBetweenAndEndIsBetween(project.getKey(), fromDate, toDate, fromDate, toDate);
    }

    @Cacheable(value = Caches.GROUP_USER_STATISTICS_CACHE, sync = true, key = "#group.uuid + #daysLimit")
    public void getGroupUserStatistics(final Group group, Optional<Integer> daysLimit) {

    }

    @Cacheable(value = Caches.GROUP_USER_STATISTICS_DIFF_CACHE, sync = true, key = "#group.uuid + #fromDate + #toDate")
    public List<UserStatisticsEntryEntity> getGroupUserStatisticsDiff(final Group group, final LocalDate fromDate, final LocalDate toDate) {
        return group.getAllProjects().stream().map(project -> getProjectUserStatisticsDiff(project, fromDate, toDate)).flatMap(List::stream).collect(Collectors.toList());

    }
}
