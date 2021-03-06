package pl.consdata.ico.sqcompanion.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.issue.IssueSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
