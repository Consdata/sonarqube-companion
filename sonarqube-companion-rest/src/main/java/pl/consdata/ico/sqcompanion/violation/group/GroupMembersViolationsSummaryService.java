package pl.consdata.ico.sqcompanion.violation.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.members.MembersViolationsSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.group.summary.EmptyGroupMemberSummaryProjection;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupMemberSummaryProjection;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationSummaryHistoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;


@Service
@RequiredArgsConstructor
@Slf4j
public class GroupViolationsSummaryService {
    private final GroupViolationSummaryHistoryRepository repository;

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

}
