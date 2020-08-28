package pl.consdata.ico.sqcompanion.violation;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViolationsHistory {

    @Singular
    private List<ViolationHistoryEntry> violationHistoryEntries;

}
