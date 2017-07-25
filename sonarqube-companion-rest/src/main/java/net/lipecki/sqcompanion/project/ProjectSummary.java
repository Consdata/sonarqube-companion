package net.lipecki.sqcompanion.project;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;
import net.lipecki.sqcompanion.violations.Violations;

/**
 * @author gregorry
 */
@Data
@Builder
public class ProjectSummary {

	private String name;
	private String key;
	private String serverId;
	private HealthStatus health;
	private Violations violations;

}
