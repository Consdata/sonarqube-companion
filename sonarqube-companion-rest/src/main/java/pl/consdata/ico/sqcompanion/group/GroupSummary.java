package pl.consdata.ico.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.violations.Violations;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupSummary {

	private String uuid;
	private String name;
	private HealthStatus healthStatus;
	private Violations violations;

}
