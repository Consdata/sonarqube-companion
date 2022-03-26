package pl.consdata.ico.sqcompanion.violation.group.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.config.model.GroupLightModel;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.members.MembersViolationsSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.ViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsSummary;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserProjectSummaryViolationHistoryEntry;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationSummaryHistoryEntry.asViolations;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupViolationsHistoryService {
    private final MemberService memberService;
    private final RepositoryService repositoryService;
    private final GroupViolationSummaryHistoryRepository repository;

    public void addToGroupHistory(UserProjectSummaryViolationHistoryEntry userEntry) {
        final List<String> groups = memberService.memberGroups(userEntry.getUserId()).stream().map(GroupLightModel::getUuid).collect(Collectors.toList());
        addToGroupHistory(userEntry, groups);
    }

    public void addToGroupHistory(UserProjectSummaryViolationHistoryEntry userEntry, LocalDate date) {
        final List<String> groups = memberService.memberGroups(userEntry.getUserId(), date.plusDays(1)).stream().map(GroupLightModel::getUuid).collect(Collectors.toList());
        addToGroupHistory(userEntry, groups);
    }

    public void addToGroupHistory(UserProjectSummaryViolationHistoryEntry userEntry, List<String> groups) {
        repositoryService.getRootGroup().getAllGroups().stream().filter(group -> group.getProject(userEntry.getProjectKey()).isPresent())
                .map(Group::getUuid)
                .filter(groups::contains)
                .map(this::getGroupsRecursive)
                .flatMap(List::stream)
                .collect(Collectors.toSet())
                .forEach(uuid -> addToGroupHistory(uuid, userEntry));
    }

    private List<String> getGroupsRecursive(String uuid) {
        List<String> output = new ArrayList<>();
        output.add(uuid);
        repositoryService.getGroup(uuid).ifPresent(group -> output.addAll(group.getParentGroups().stream().map(GroupLightModel::getUuid).collect(Collectors.toList())));
        return output;
    }

    private void addToGroupHistory(String groupId, UserProjectSummaryViolationHistoryEntry userEntry) {
        log.info("      Add user {} violations to group summary {} for project{}", userEntry.getUserId(), groupId, userEntry.getProjectKey());
        repository.save(repository.findFirstByGroupIdAndProjectKeyAndUserIdAndDate(groupId, userEntry.getProjectKey(), userEntry.getUserId(), userEntry.getDate())
                .map(entry -> {
                    entry.setBlockers(entry.getBlockers() + userEntry.getBlockers());
                    entry.setCriticals(entry.getCriticals() + userEntry.getCriticals());
                    entry.setMajors(entry.getMajors() + userEntry.getMajors());
                    entry.setMinors(entry.getMinors() + userEntry.getMinors());
                    entry.setInfos(entry.getInfos() + userEntry.getInfos());
                    return entry;
                })
                .orElse(GroupViolationSummaryHistoryEntry.builder()
                        .id(GroupViolationSummaryHistoryEntry.combineId(userEntry.getServerId(), groupId, userEntry.getProjectKey(), userEntry.getUserId(), userEntry.getDate()))
                        .serverId(userEntry.getServerId())
                        .groupId(groupId)
                        .userId(userEntry.getUserId())
                        .date(userEntry.getDate())
                        .projectKey(userEntry.getProjectKey())
                        .blockers(userEntry.getBlockers())
                        .criticals(userEntry.getCriticals())
                        .majors(userEntry.getMajors())
                        .minors(userEntry.getMinors())
                        .infos(userEntry.getInfos())
                        .build()));
    }

    public ViolationsHistory getGroupViolationsHistory(Group group, Optional<Integer> daysLimit) {
        return getGroupViolationsHistory(group, LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1));
    }

    @Cacheable(value = Caches.GROUP_VIOLATIONS_HISTORY_CACHE, sync = true, key = "#group.uuid + #from + #to")
    public ViolationsHistory getGroupViolationsHistory(Group group, LocalDate from, LocalDate to) {
        return new ViolationsHistory(repository.findAllByGroupIdAndDateBetween(group.getUuid(),from, to)
                .stream()
                .collect(Collectors.groupingBy(GroupViolationSummaryHistoryEntry::getDate))
                .entrySet()
                .stream()
                .map(item -> ViolationHistoryEntry.builder()
                        .date(item.getKey())
                        .violations(asViolations(item.getValue()))
                        .build()).sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
                .collect(Collectors.toList()));
    }


    public GroupViolationsHistoryDiff getGroupViolationsHistoryDiff(Group group, LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    public ViolationsHistory getProjectViolationsHistory(Group group, Project project, Optional<Integer> daysLimit) {
        return new ViolationsHistory(repository.findAllByGroupIdAndProjectKeyAndDateBetween(group.getUuid(), project.getKey(), LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1))
                .stream()
                .collect(Collectors.groupingBy(GroupViolationSummaryHistoryEntry::getDate))
                .entrySet()
                .stream()
                .map(item -> ViolationHistoryEntry.builder()
                        .date(item.getKey())
                        .violations(asViolations(item.getValue()))
                        .build())
                .sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
                .collect(Collectors.toList()));
    }

    public ProjectViolationsSummary getProjectViolationsHistoryDiff(Group group, Project project, LocalDate fromDate, LocalDate toDate) {
        return null;
    }


    public List<ProjectViolationsSummary> getGroupProjectsViolationsHistoryDiff(Group group, Optional<List<String>> membersIds, Optional<Integer> daysLimit) {
        return getGroupProjectsViolationsHistoryDiff(group, LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1), membersIds);
    }

    public List<ProjectViolationsSummary> getGroupProjectsViolationsHistoryDiff(Group group, LocalDate fromDate, LocalDate toDate, Optional<List<String>> membersIds) {
        if (membersIds.isPresent()) {
            return emptyList();
        } else {
            Map<String, List<GroupProjectSummaryProjection>> fromViolations = repository.groupByProjects(group.getUuid(), fromDate).stream().collect(Collectors.groupingBy(GroupProjectSummaryProjection::projectKey));
            Map<String, List<GroupProjectSummaryProjection>> toViolations = repository.groupByProjects(group.getUuid(), toDate).stream().collect(Collectors.groupingBy(GroupProjectSummaryProjection::projectKey));

            return toViolations.entrySet()
                    .stream()
                    .map(entry -> {
                        GroupProjectSummaryProjection toDateEntry = entry.getValue().get(0); // TODO if project removed
                        GroupProjectSummaryProjection fromDateEntry = ofNullable(fromViolations.get(entry.getKey())).orElse(singletonList(new EmptyGroupProjectSummaryProjection(fromDate, entry.getKey()))).get(0);
                        return createProjectViolationsHistoryDiff(fromDateEntry, toDateEntry, fromDate, toDate);
                    })
                    .collect(Collectors.toList());

        }
    }

    private ProjectViolationsSummary createProjectViolationsHistoryDiff(GroupProjectSummaryProjection fromDateEntry, GroupProjectSummaryProjection toDateEntry, LocalDate fromDate, LocalDate toDate) {
        final Violations addedViolations = Violations.empty();
        final Violations removedViolations = Violations.empty();

        final Violations violationsDiff = Violations
                .builder()
                .blockers(toDateEntry.blockers() - fromDateEntry.blockers())
                .criticals(toDateEntry.criticals() - fromDateEntry.criticals())
                .majors(toDateEntry.majors() - fromDateEntry.majors())
                .minors(toDateEntry.minors() - fromDateEntry.minors())
                .infos(toDateEntry.infos() - fromDateEntry.infos())
                .build();
        mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violationsDiff);
        return ProjectViolationsSummary
                .builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .addedViolations(addedViolations)
                .removedViolations(removedViolations)
                .projectKey(toDateEntry.projectKey())
                .violationsDiff(violationsDiff)
                .build();
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

    public List<MembersViolationsSummary> getGroupMembersViolationsHistoryDiff(Group group, Optional<List<String>> projects, Optional<Integer> daysLimit) {
        return getGroupMembersViolationsHistoryDiff(group, LocalDate.now().minusDays(daysLimit.orElse(30)), LocalDate.now().minusDays(1), projects);
    }

    public List<MembersViolationsSummary> getGroupMembersViolationsHistoryDiff(Group group, LocalDate fromDate, LocalDate toDate, Optional<List<String>> projects) {
        if (projects.isPresent()) {
            return emptyList();
        } else {
            Map<String, List<GroupMemberSummaryProjection>> fromViolations = repository.groupByMembers(group.getUuid(), fromDate).stream().collect(Collectors.groupingBy(GroupMemberSummaryProjection::userId));
            Map<String, List<GroupMemberSummaryProjection>> toViolations = repository.groupByMembers(group.getUuid(), toDate).stream().collect(Collectors.groupingBy(GroupMemberSummaryProjection::userId));

            return toViolations.entrySet()
                    .stream()
                    .map(entry -> {
                        GroupMemberSummaryProjection toDateEntry = entry.getValue().get(0); // TODO if project removed
                        GroupMemberSummaryProjection fromDateEntry = ofNullable(fromViolations.get(entry.getKey())).orElse(singletonList(new EmptyGroupMemberSummaryProjection(fromDate, entry.getKey()))).get(0);
                        return createMemberViolationsHistoryDiff(fromDateEntry, toDateEntry, fromDate, toDate);
                    })
                    .collect(Collectors.toList());

        }
    }

    public Violations getViolations(String groupId, LocalDate localDate) {
        return repository.groupViolations(groupId, localDate).map(projection -> Violations.builder()
                .blockers(projection.blockers())
                .criticals(projection.criticals())
                .majors(projection.majors())
                .minors(projection.minors())
                .infos(projection.infos())
                .build()).orElse(Violations.empty());
    }

    private MembersViolationsSummary createMemberViolationsHistoryDiff(GroupMemberSummaryProjection fromDateEntry, GroupMemberSummaryProjection toDateEntry, LocalDate fromDate, LocalDate toDate) {
        final Violations addedViolations = Violations.empty();
        final Violations removedViolations = Violations.empty();

        final Violations violationsDiff = Violations
                .builder()
                .blockers(toDateEntry.blockers() - fromDateEntry.blockers())
                .criticals(toDateEntry.criticals() - fromDateEntry.criticals())
                .majors(toDateEntry.majors() - fromDateEntry.majors())
                .minors(toDateEntry.minors() - fromDateEntry.minors())
                .infos(toDateEntry.infos() - fromDateEntry.infos())
                .build();
        mergeProjectViolationsToAddedOrRemovedGroupViolations(addedViolations, removedViolations, violationsDiff);
        return MembersViolationsSummary
                .builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .addedViolations(addedViolations)
                .removedViolations(removedViolations)
                .uuid(toDateEntry.userId())
                .name(toDateEntry.userId())
                .violationsDiff(violationsDiff)
                .build();
    }

    public Violations getViolationsDiff(String uuid, LocalDate from, LocalDate to) {
        Violations violationsFrom = getViolations(uuid, from);
        Violations violationsTo = getViolations(uuid, to);
        return Violations.builder()
                .blockers(violationsTo.getBlockers() - violationsFrom.getBlockers())
                .criticals(violationsTo.getCriticals() - violationsFrom.getCriticals())
                .majors(violationsTo.getMajors() - violationsFrom.getMajors())
                .minors(violationsTo.getMinors() - violationsFrom.getMinors())
                .infos(violationsTo.getInfos() - violationsFrom.getInfos())
                .build();
    }

}
