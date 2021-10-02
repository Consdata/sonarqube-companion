package pl.consdata.ico.sqcompanion.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersViolationsSummary {

    private String uuid;
    private String name;
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
