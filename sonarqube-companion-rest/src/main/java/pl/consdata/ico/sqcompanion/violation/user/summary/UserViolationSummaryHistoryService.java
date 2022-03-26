package pl.consdata.ico.sqcompanion.violation.user.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.violation.ViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectHistoryEntryEntity;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserViolationSummaryHistoryService {

    private final UserViolationHistoryRepository repository;
    private final MemberService memberService;
    private final AppConfig appConfig;
    private final HealthCheckService healthCheckService;


    public ViolationsHistory userViolationsHistory(String user) {
        return ViolationsHistory.builder()
                .violationHistoryEntries(ViolationHistoryEntry.groupByDate(repository.findByUserId(user)))
                .build();
    }

    public List<ProjectSummary> getProjectSummaries(final List<Project> allProjects, String uuid) {
        return allProjects
                .stream()
                .map(p -> getProjectSummary(p, uuid))
                .collect(Collectors.toList());
    }

    @Cacheable(value = Caches.GROUP_PROJECT_SUMMARY_CACHE, sync = true, key = "#uuid  + #project.key")
    public ProjectSummary getProjectSummary(final Project project, String uuid) {
        return asProjectSummary(project, uuid);
    }

    private ProjectSummary asProjectSummary(final Project p, final String uuid) {
        List<UserProjectSummaryViolationHistoryEntry> historyEntries = memberService.membersAliases(uuid)
                .stream()
                .map(user -> repository.findFirstByProjectKeyAndUserIdOrderByDateDesc(p.getKey(), user))
                .collect(Collectors.toList());


        final ProjectSummary.ProjectSummaryBuilder builder = ProjectSummary
                .builder()
                .name(p.getName())
                .key(p.getKey())
                .serverId(p.getServerId())
                .serverUrl(p.getServerUrl())
                .healthStatus(healthCheckService.checkHealth(p));

        final Violations violations = Violations.empty();
        historyEntries.stream().filter(Objects::nonNull).forEach(entry -> violations.addViolations(ViolationHistoryEntry.of(entry).getViolations()));
        builder.violations(violations);


        return builder.build();
    }

    @Cacheable(value = Caches.GROUP_USER_VIOLATIONS_HISTORY_DIFF_CACHE, sync = true, key = "#group.uuid + #fromDate + #toDate")
    public GroupViolationsHistoryDiff getGroupsUserViolationsHistoryDiff(final Group group, final LocalDate fromDate, final LocalDate toDate) {
        Set<String> users = memberService.membersAliases(group.getUuid());
        final List<ProjectViolationsSummary> projectDiffs = group
                .getAllProjects()
                .stream()
                .filter(project -> repository.existsByProjectKeyAndUserIdIsIn(project.getKey(), users))
                .map(getProjectViolationsHistoryDiffMappingFunction(users, fromDate, toDate))
                .collect(Collectors.toList());

        final Violations addedViolations = Violations.builder().build();
        final Violations removedViolations = Violations.builder().build();
        projectDiffs
                .stream()
                .map(ProjectViolationsSummary::getViolationsDiff)
                .forEach(violations -> mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violations));
        return GroupViolationsHistoryDiff
                .builder()
                .groupDiff(Violations.sumViolations(addedViolations, removedViolations))
                .addedViolations(addedViolations)
                .removedViolations(removedViolations)
                .projectDiffs(projectDiffs)
                .build();
    }

    private LocalDate getToDate(LocalDate toDate) {
        if (DAYS.between(LocalDate.now(), toDate) == 0L) {
            return toDate.minusDays(1);
        } else {
            return toDate;
        }
    }

    private Function<Project, ProjectViolationsSummary> getProjectViolationsHistoryDiffMappingFunction(Set<String> users, LocalDate fromDate, LocalDate toDate) {
        return project -> {
            LocalDate from = repository.findFirstByProjectKeyAndUserIdIsInOrderByDateAsc(project.getKey(), users)
                    .filter(entry -> fromDate.isBefore(entry.getDate()))
                    .map(UserProjectSummaryViolationHistoryEntry::getDate)
                    .orElse(fromDate);

            LocalDate to = repository.findFirstByProjectKeyAndUserIdIsInOrderByDateDesc(project.getKey(), users)
                    .filter(entry -> fromDate.isBefore(entry.getDate()))
                    .map(UserProjectSummaryViolationHistoryEntry::getDate)
                    .orElse(getToDate(toDate));

            final List<UserProjectSummaryViolationHistoryEntry> fromDateEntries =
                    repository
                            .findByProjectKeyAndUserIdIsInAndDateEquals(project.getKey(), users, from);
            final List<UserProjectSummaryViolationHistoryEntry> toDateEntries = repository
                    .findByProjectKeyAndUserIdIsInAndDateEquals(project.getKey(), users, getToDate(to));

            if (!fromDateEntries.isEmpty() && toDateEntries.isEmpty()) {
                throw new SQCompanionException(
                        String.format(
                                "Can't get diff for project with history and without to date analyses [projectKey=%s]",
                                project.getKey()
                        )
                );
            }

            final Violations fromDateEntriesSum = Violations.empty();
            final Violations toDateEntriesSum = Violations.empty();

            fromDateEntries.forEach(entry -> fromDateEntriesSum.addViolations(ViolationHistoryEntry.of(entry).getViolations()));
            toDateEntries.forEach(entry -> toDateEntriesSum.addViolations(ViolationHistoryEntry.of(entry).getViolations()));

            final Violations violationsDiff = Violations
                    .builder()
                    .blockers(toDateEntriesSum.getBlockers() - fromDateEntriesSum.getBlockers())
                    .criticals(toDateEntriesSum.getCriticals() - fromDateEntriesSum.getCriticals())
                    .majors(toDateEntriesSum.getMajors() - fromDateEntriesSum.getMajors())
                    .minors(toDateEntriesSum.getMinors() - fromDateEntriesSum.getMinors())
                    .infos(toDateEntriesSum.getInfos() - fromDateEntriesSum.getInfos())
                    .build();

            final Violations addedViolations = Violations.builder().build();
            final Violations removedViolations = Violations.builder().build();
            mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violationsDiff);
            return ProjectViolationsSummary
                    .builder()
                    .projectId(project.getId())
                    .fromDate(from)
                    .toDate(to)
                    .projectKey(project.getKey())
                    .violationsDiff(violationsDiff)
                    .addedViolations(addedViolations)
                    .removedViolations(removedViolations)
                    .build();
        };
    }


    private void mergeProjectViolationsToAddedOrRemovedGroupViolations(
            final Violations addedViolations,
            final Violations removedViolations,
            final Violations violations) {
        if (violations.getBlockers() >= 0) {
            addedViolations.addBlockers(violations.getBlockers());
        } else {
            removedViolations.addBlockers(Math.abs(violations.getBlockers()));
        }
        if (violations.getCriticals() >= 0) {
            addedViolations.addCriticals(violations.getCriticals());
        } else {
            removedViolations.addCriticals(Math.abs(violations.getCriticals()));
        }
        if (violations.getMajors() >= 0) {
            addedViolations.addMajors(violations.getMajors());
        } else {
            removedViolations.addMajors(Math.abs(violations.getMajors()));
        }
        if (violations.getMinors() >= 0) {
            addedViolations.addMinors(violations.getMinors());
        } else {
            removedViolations.addMinors(Math.abs(violations.getMinors()));
        }
        if (violations.getInfos() >= 0) {
            addedViolations.addInfos(violations.getInfos());
        } else {
            removedViolations.addInfos(Math.abs(violations.getInfos()));
        }
    }

    public ViolationsHistory getGroupViolationsHistory(final Group group, Optional<Integer> daysLimit) {
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(
                        ViolationHistoryEntry.groupByDate(
                                group.getAllProjects()
                                        .stream()
                                        .flatMap(project -> getProjectViolationsHistory(project, daysLimit, memberService.membersAliases(group.getUuid()))
                                                .getViolationHistoryEntries().stream())
                        )
                )
                .build();
    }

    private long getDaysLimit(final Project project, Optional<Integer> daysLimit, Set<String> users) {
        Optional<UserProjectSummaryViolationHistoryEntry> entry = repository.findFirstByProjectKeyAndUserIdIsInOrderByDateAsc(project.getKey(), users);
        LocalDate localDate = LocalDate.now();
        if (daysLimit.isPresent() && entry.isPresent()) {
            return Long.min(DAYS.between(localDate.minusDays(daysLimit.get()), localDate), DAYS.between(entry.get().getDate(), localDate));
        } else if (entry.isPresent()) {
            return DAYS.between(localDate, entry.get().getDate());
        }
        return 0;
    }

    public ViolationsHistory getProjectViolationsHistory(final Project project, Optional<Integer> daysLimit, Set<String> users) {
        List<UserProjectSummaryViolationHistoryEntry> history;
        if (daysLimit.isPresent()) {
            history = repository.findAllByProjectKeyAndUserIdIsInAndDateGreaterThanEqual(
                    project.getKey(),
                    users,
                    LocalDate.now().minusDays(getDaysLimit(project, daysLimit, users)));
        } else {
            history = repository.findAllByProjectKeyAndUserIdIsIn(project.getKey(), users);
        }
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(
                        history.stream()
                                .collect(Collectors.groupingBy(UserProjectSummaryViolationHistoryEntry::getDate))
                                .entrySet().stream()
                                .map(entry ->
                                        ProjectHistoryEntryEntity.builder()
                                                .date(entry.getKey())
                                                .projectKey(project.getKey())
                                                .serverId(project.getServerId())
                                                .id(project.getId())
                                                .blockers(entry.getValue().stream().map(UserProjectSummaryViolationHistoryEntry::getBlockers).reduce(0, Integer::sum))
                                                .criticals(entry.getValue().stream().map(UserProjectSummaryViolationHistoryEntry::getCriticals).reduce(0, Integer::sum))
                                                .majors(entry.getValue().stream().map(UserProjectSummaryViolationHistoryEntry::getMajors).reduce(0, Integer::sum))
                                                .minors(entry.getValue().stream().map(UserProjectSummaryViolationHistoryEntry::getMinors).reduce(0, Integer::sum))
                                                .infos(entry.getValue().stream().map(UserProjectSummaryViolationHistoryEntry::getInfos).reduce(0, Integer::sum))
                                                .build()
                                )
                                .map(ViolationHistoryEntry::of)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Cacheable(value = Caches.GROUP_PROJECT_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#group.uuid + #project.key + #daysLimit")
    public ViolationsHistory getProjectViolationsHistory(Group group, Project project, Optional<Integer> daysLimit) {
        return getProjectViolationsHistory(project, daysLimit, memberService.membersAliases(group.getUuid()));
    }

    @Cacheable(value = Caches.GROUP_PROJECT_VIOLATIONS_HISTORY_DIFF_CACHE, sync = true, key = "#group.uuid  + #project.key + #fromDate + #toDate")
    public ProjectViolationsSummary getProjectViolationsHistoryDiff(Group group, Project project, LocalDate fromDate, LocalDate toDate) {
        return getProjectViolationsHistoryDiffMappingFunction(memberService.membersAliases(group.getUuid()), fromDate, toDate).apply(project);
    }

}
