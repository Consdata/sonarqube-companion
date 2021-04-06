package pl.consdata.ico.sqcompanion.violation.project;

import lombok.*;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupViolationsHistoryDiff {

    private Violations groupDiff;
    private Violations addedViolations;
    private Violations removedViolations;
    private List<ProjectViolationsHistoryDiff> projectDiffs;

    public static GroupViolationsHistoryDiff empty() {
        return GroupViolationsHistoryDiff.builder()
                .addedViolations(Violations.builder()
                        .blockers(0)
                        .criticals(0)
                        .majors(0)
                        .minors(0)
                        .infos(0)
                        .build())
                .removedViolations(Violations.builder()
                        .blockers(0)
                        .criticals(0)
                        .majors(0)
                        .minors(0)
                        .infos(0)
                        .build())
                .groupDiff(Violations.builder()
                        .blockers(0)
                        .criticals(0)
                        .majors(0)
                        .minors(0)
                        .infos(0)
                        .build())
                .projectDiffs(new ArrayList<>())
                .build();
    }
}
