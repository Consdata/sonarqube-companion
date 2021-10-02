package pl.consdata.ico.sqcompanion.violation.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.group.summary.EmptyGroupProjectSummaryProjection;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupProjectSummaryProjection;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationSummaryHistoryRepository;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsSummary;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;


@Service
@RequiredArgsConstructor
@Slf4j
public class GroupProjectsViolationsSummaryService {
    private final GroupViolationSummaryHistoryRepository repository;

    public List<ProjectViolationsSummary> getGroupProjectsViolationsHistoryDiff(Group group, LocalDate fromDate, LocalDate toDate) {
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

}
