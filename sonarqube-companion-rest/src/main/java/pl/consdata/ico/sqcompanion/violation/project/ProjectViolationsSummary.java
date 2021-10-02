package pl.consdata.ico.sqcompanion.violation.project;

import lombok.*;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectViolationsSummary {

    private String projectKey;
    private String projectId;
    private Violations violationsDiff;
    private Violations addedViolations;
    private Violations removedViolations;
    private LocalDate fromDate;
    private LocalDate toDate;

    public String getFromDateString() {
        return fromDate.toString();
    }

    public String getToDateString() {
        return toDate.toString();
    }

}
