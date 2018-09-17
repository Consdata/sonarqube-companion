package pl.consdata.ico.sqcompanion.violation;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.SQCompanionException;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
public class ViolationHistoryEntry {

    private LocalDate date;
    private Violations violations;

    public static ViolationHistoryEntry sumEntries(final ViolationHistoryEntry a, final ViolationHistoryEntry b) {
        if (!Objects.equals(a.getDate(), b.getDate())) {
            throw new SQCompanionException("Can't sum violation entries from different dates");
        }
        return ViolationHistoryEntry
                .builder()
                .date(a.getDate())
                .violations(Violations.sumViolations(a.getViolations(), b.getViolations()))
                .build();
    }

    public String getDateString() {
        return date.toString();
    }

}
