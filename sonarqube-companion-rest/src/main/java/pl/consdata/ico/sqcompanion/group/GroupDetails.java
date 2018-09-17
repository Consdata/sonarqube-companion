package pl.consdata.ico.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.config.GroupEvent;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.issue.IssueSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;

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
    private List<GroupEvent> events;

}
