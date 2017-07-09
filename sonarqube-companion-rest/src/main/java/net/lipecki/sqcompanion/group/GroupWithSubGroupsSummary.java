package net.lipecki.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupWithSubGroupsSummary {

	private String uuid;
	private String name;
	private HealthStatus healthStatus;
	private List<GroupWithSubGroupsSummary> groups;

}
