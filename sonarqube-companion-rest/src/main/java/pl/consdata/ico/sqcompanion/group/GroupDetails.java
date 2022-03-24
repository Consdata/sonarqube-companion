package pl.consdata.ico.sqcompanion.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.issue.IssueSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.repository.Group;
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
    private String parentUuid;
    private String name;
    private String description;
    private HealthStatus healthStatus;
    private Integer projects;
    private Integer members;
    private Integer events;
    private List<GroupDetails> groups;
}
