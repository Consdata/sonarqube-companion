package net.lipecki.sqcompanion.overview;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupOverview {

	private String uuid;
	private String name;
	private HealthStatus healthStatus;
	private List<GroupOverview> groups;

}
