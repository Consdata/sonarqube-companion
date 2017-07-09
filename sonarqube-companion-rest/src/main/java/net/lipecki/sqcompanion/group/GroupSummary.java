package net.lipecki.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupSummary {

	private String uuid;
	private String name;
	private HealthStatus healthStatus;

}
