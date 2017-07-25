package net.lipecki.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;
import net.lipecki.sqcompanion.issue.IssueSummary;
import net.lipecki.sqcompanion.project.ProjectSummary;
import net.lipecki.sqcompanion.violations.Violations;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupDetails {

	private String uuid;
	private String name;
	private HealthStatus healthStatus;
	private Violations violations;
	private List<GroupSummary> groups;
	private List<ProjectSummary> projects;
	private List<IssueSummary> issues;

}
