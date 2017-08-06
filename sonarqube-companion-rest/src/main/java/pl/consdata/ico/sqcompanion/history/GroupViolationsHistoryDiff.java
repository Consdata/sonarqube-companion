package pl.consdata.ico.sqcompanion.history;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupViolationsHistoryDiff {

    private final Violations groupDiff;
    private final List<ProjectViolationsHistoryDiff> projectDiffs;

}
