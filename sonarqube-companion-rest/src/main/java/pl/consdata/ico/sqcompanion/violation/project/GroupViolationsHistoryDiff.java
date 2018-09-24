package pl.consdata.ico.sqcompanion.violation.project;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryDiff;

import java.util.List;

@Data
@Builder
public class GroupViolationsHistoryDiff {

    private final Violations groupDiff;
    private final Violations addedViolations;
    private final Violations removedViolations;
    private final List<ProjectViolationsHistoryDiff> projectDiffs;

}
