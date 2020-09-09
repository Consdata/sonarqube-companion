package pl.consdata.ico.sqcompanion.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.violation.Violations;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupSummary {

    private String uuid;
    private String name;
    private HealthStatus healthStatus;
    private Violations violations;

}
