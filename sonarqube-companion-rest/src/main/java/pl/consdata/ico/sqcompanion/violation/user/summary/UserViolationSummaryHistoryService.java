package pl.consdata.ico.sqcompanion.violation.user.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.SQCompanionException;
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
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryDiff;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                .map(p -> asProjectSummary(p, uuid))
                .collect(Collectors.toList());
    }

    public ProjectSummary getProjectSummary(final Project project, String uuid) {
        return asProjectSummary(project, uuid);
    }

    public ProjectSummary getProjectSummary(final Project project) {
        return asProjectSummary(project, appConfig.getRootGroup().getUuid());
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

    //  @Cacheable(value = Caches.GROUP_USER_VIOLATIONS_HISTORY_DIFF_CACHE, sync = true, key = "#group.uuid + #userId + #fromDate + #toDate")
    public GroupViolationsHistoryDiff getGroupsUserViolationsHistoryDiff(final Group group, final LocalDate fromDate, final LocalDate toDate) {
        final List<ProjectViolationsHistoryDiff> projectDiffs = group
                .getAllProjects()
                .stream()
                // TODO verify
                //.filter(project -> repository.existsByProjectKeyAndUserId(project.getKey(), userId))
                .map(getProjectViolationsHistoryDiffMappingFunction(memberService.membersAliases(group.getUuid()), fromDate, toDate)) //TODO lista userów
                .collect(Collectors.toList());

        final Violations addedViolations = Violations.builder().build();
        final Violations removedViolations = Violations.builder().build();
        projectDiffs
                .stream()
                .map(ProjectViolationsHistoryDiff::getViolationsDiff)
                .forEach(violations -> mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violations));
        return GroupViolationsHistoryDiff
                .builder()
                .groupDiff(Violations.sumViolations(addedViolations, removedViolations))
                .addedViolations(addedViolations)
                .removedViolations(removedViolations)
                .projectDiffs(projectDiffs)
                .build();
    }

    private void projectViolationsHistoryDiffs(Map<String, ProjectViolationsHistoryDiff> base, List<ProjectViolationsHistoryDiff> diffs) {
        diffs.stream().forEach(diff -> {
            if (base.containsKey(diff.getProjectKey())) {
                base.get(diff.getProjectKey()).setViolationsDiff(Violations.sumViolations(base.get(diff.getProjectKey()).getViolationsDiff(), diff.getViolationsDiff()));
            } else {
                base.put(diff.getProjectKey(), diff);
            }
        });
    }

    private Function<Project, ProjectViolationsHistoryDiff> getProjectViolationsHistoryDiffMappingFunction(String userId, LocalDate fromDate, LocalDate toDate) {
        return project -> {
            final Optional<UserProjectSummaryViolationHistoryEntry> fromDateEntryOptional =
                    repository
                            .findByProjectKeyAndUserIdAndDateEquals(project.getKey(), userId, fromDate);
            final Optional<UserProjectSummaryViolationHistoryEntry> toDateEntryOptional = repository
                    .findByProjectKeyAndUserIdAndDateEquals(project.getKey(), userId, toDate);

            if (fromDateEntryOptional.isPresent() && !toDateEntryOptional.isPresent()) {
                throw new SQCompanionException(
                        String.format(
                                "Can't get diff for project with history and without to date analyses [projectKey=%s]",
                                project.getKey()
                        )
                );
            }
            final UserProjectSummaryViolationHistoryEntry fromDateEntry = fromDateEntryOptional.orElse(UserProjectSummaryViolationHistoryEntry.empty());
            final UserProjectSummaryViolationHistoryEntry toDateEntry = toDateEntryOptional.orElse(UserProjectSummaryViolationHistoryEntry.empty());

            final Violations violationsDiff = Violations
                    .builder()
                    .blockers(toDateEntry.getBlockers() - fromDateEntry.getBlockers())
                    .criticals(toDateEntry.getCriticals() - fromDateEntry.getCriticals())
                    .majors(toDateEntry.getMajors() - fromDateEntry.getMajors())
                    .minors(toDateEntry.getMinors() - fromDateEntry.getMinors())
                    .infos(toDateEntry.getInfos() - fromDateEntry.getInfos())
                    .build();

            // TODO replace by user diff
            return ProjectViolationsHistoryDiff
                    .builder()
                    .projectId(project.getId())
                    .fromDate(fromDate)
                    .toDate(toDate)
                    .projectKey(project.getKey())
                    .violationsDiff(violationsDiff)
                    .build();
        };
    }


    private Function<Project, ProjectViolationsHistoryDiff> getProjectViolationsHistoryDiffMappingFunction(List<String> users, LocalDate fromDate, LocalDate toDate) {
        return project -> {
            final List<UserProjectSummaryViolationHistoryEntry> fromDateEntries =
                    repository
                            .findByProjectKeyAndUserIdIsInAndDateEquals(project.getKey(), users, fromDate);
            final List<UserProjectSummaryViolationHistoryEntry> toDateEntries = repository
                    .findByProjectKeyAndUserIdIsInAndDateEquals(project.getKey(), users, toDate);

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

            // TODO replace by user diff
            return ProjectViolationsHistoryDiff
                    .builder()
                    .projectId(project.getId())
                    .fromDate(fromDate)
                    .toDate(toDate)
                    .projectKey(project.getKey())
                    .violationsDiff(violationsDiff)
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

    //@Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#group.uuid + #daysLimit")
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


    //@Cacheable(value = Caches.PROJECT_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#project.getId() + #daysLimit")
    public ViolationsHistory getProjectViolationsHistory(final Project project, Optional<Integer> daysLimit, List<String> users) {
        List<UserProjectSummaryViolationHistoryEntry> history;
        if (daysLimit.isPresent()) {
            history = repository.findAllByProjectKeyAndUserIdIsInAndDateGreaterThanEqual(
                    project.getKey(),
                    users,
                    LocalDate.now().minusDays(daysLimit.get()));
        } else {
            history = repository.findAllByProjectKeyAndUserIdIsIn(project.getKey(), users);
        }
        return ViolationsHistory
                .builder()
                .violationHistoryEntries(
                        history.stream()
                                .map(ViolationHistoryEntry::of)
                                .collect(Collectors.toList())
                )
                .build();
    }

    //Cachable
    public ViolationsHistory getProjectViolationsHistory(Group group, Project project, Optional<Integer> daysLimit) {
        return getProjectViolationsHistory(project, daysLimit, memberService.membersAliases(group.getUuid()));
    }

    //Cacheble
    public ProjectViolationsHistoryDiff getProjectViolationsHistoryDiff(Group group, Project project, LocalDate fromDate, LocalDate toDate) {
        return getProjectViolationsHistoryDiffMappingFunction(memberService.membersAliases(group.getUuid()), fromDate, toDate).apply(project);
    }

}
