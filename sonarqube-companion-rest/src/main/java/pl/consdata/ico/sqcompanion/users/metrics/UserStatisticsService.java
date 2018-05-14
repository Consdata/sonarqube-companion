package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.IssueFilter;
import pl.consdata.ico.sqcompanion.sonarqube.RemoteSonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssueSeverity;
import pl.consdata.ico.sqcompanion.sonarqube.sqapi.SQUser;
import pl.consdata.ico.sqcompanion.statistics.UserStatisticConfig;
import pl.consdata.ico.sqcompanion.statistics.UserStatisticsRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserStatisticsService {
    private RemoteSonarQubeFacade remoteSonarQubeFacade;
    private UserStatisticsRepository userStatisticsRepository;

    public UserStatisticsService(RemoteSonarQubeFacade remoteSonarQubeFacade,
                                 UserStatisticsRepository userStatisticsRepository) {
        this.remoteSonarQubeFacade = remoteSonarQubeFacade;
        this.userStatisticsRepository = userStatisticsRepository;
    }


    private List<SQUser> getUsersForProject(final Project project) {
        return remoteSonarQubeFacade.getUsers(project.getServerId(), false);
    }

    private List<SonarQubeIssue> getIssuesForUsers(final List<SQUser> users, final Project project, final UserStatisticConfig config) {
        IssueFilter.IssueFilterBuilder builder = IssueFilter.builder();
        users.forEach(user -> builder.author(user.getEmail()));
        builder.createdAfter(config.getFrom().format(DateTimeFormatter.ISO_DATE));
        return remoteSonarQubeFacade.getIssues(project.getServerId(), builder.projectKey(project.getKey()).resolved(false).build());

    }

    private void syncUsersStatsPerProject(final Project project, List<SQUser> users, UserStatisticConfig config) {
        List<SonarQubeIssue> issues = getIssuesForUsers(users, project, config);
        Map<String, List<SonarQubeIssue>> issueMap = mapIssuesToAuthorIssueMap(issues, users);
        Set<UserStatisticsEntryEntity> entries = new HashSet<>();
        users.forEach(user -> entries.addAll(groupByDate(user, issueMap.get(user.getEmail()), project)));
        log.info("Storing users statistics for project [project={}]", project);
        userStatisticsRepository.saveAll(entries);
    }


    private Set<UserStatisticsEntryEntity> groupByDate(SQUser user, List<SonarQubeIssue> issues, Project project) {
        Map<String, List<SonarQubeIssue>> issuesByDate = issues.stream().collect(Collectors.groupingBy(issue -> new SimpleDateFormat("yyyy-MM-dd").format(issue.getCreationDate())));
        return issuesByDate.entrySet().stream().map(entry -> createUserStatisticsEntry(project, user, entry.getValue(), entry.getKey())).collect(Collectors.toSet());

    }

    private UserStatisticsEntryEntity createUserStatisticsEntry(final Project project, final SQUser user, final List<SonarQubeIssue> issues, String date) {
        String id = UserStatisticsEntryEntity.combineId(project.getServerId(), project.getKey(), user.getEmail(), date);
        return UserStatisticsEntryEntity.builder()
                .id(id)
                .user(user.getEmail())
                .projectKey(project.getKey())
                .serverId(project.getServerId())
                .date(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .blockers(countBySeverity(issues, SonarQubeIssueSeverity.BLOCKER))
                .criticals(countBySeverity(issues, SonarQubeIssueSeverity.CRITICAL))
                .majors(countBySeverity(issues, SonarQubeIssueSeverity.MAJOR))
                .minors(countBySeverity(issues, SonarQubeIssueSeverity.MINOR))
                .infos(countBySeverity(issues, SonarQubeIssueSeverity.INFO))
                .build();
    }

    private long countBySeverity(List<SonarQubeIssue> issues, SonarQubeIssueSeverity severity) {
        return issues.stream().filter(issue -> issue.getSeverity().equals(severity)).count();
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

    @Cacheable(value = Caches.PROJECT_USER_STATISTICS_CACHE, sync = true, key = "#project.getId() + #fromDate + #toDate")
    public List<UserStatisticsEntryEntity> getProjectUserStatistics(final Project project, final LocalDate fromDate, final LocalDate toDate) {
        return userStatisticsRepository.findAllByProjectKeyAndDateIsBetween(project.getKey(), fromDate, toDate);
    }

    @Cacheable(value = Caches.GROUP_USER_STATISTICS_CACHE, sync = true, key = "#group.uuid + #fromDate + #toDate")
    public List<UserStatisticsEntryEntity> getGroupUserStatistics(final Group group, final LocalDate fromDate, final LocalDate toDate) {
        return group.getAllProjects().stream().map(project -> getProjectUserStatistics(project, fromDate, toDate)).flatMap(List::stream).collect(Collectors.toList());
    }
}
