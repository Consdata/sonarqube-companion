package pl.consdata.ico.sqcompanion.violation.project;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class ProjectViolationsHistoryDiff {

    private final String projectKey;
    private final String projectId;
    private final Violations violationsDiff;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public String getFromDateString() {
        return fromDate.toString();
    }

    public String getToDateString() {
        return toDate.toString();
    }

}
