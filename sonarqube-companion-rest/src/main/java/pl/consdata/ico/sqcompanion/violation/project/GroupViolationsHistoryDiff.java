package pl.consdata.ico.sqcompanion.violation.project;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class GroupViolationsHistoryDiff {

    private final Violations groupDiff;
    private final Violations addedViolations;
    private final Violations removedViolations;
    private final List<ProjectViolationsHistoryDiff> projectDiffs;

}
