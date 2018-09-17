package pl.consdata.ico.sqcompanion.overview;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class GroupOverview {

    @ApiModelProperty(value = "Unique identifier", example = "22160a1d-8978-4e0b-b70c-2e07f35e10c1", required = true)
    private String uuid;
    @ApiModelProperty(value = "User friendly name", example = "SonarQube Companion", required = true)
    private String name;
    @ApiModelProperty(value = "Health status", example = "HEALTHY", required = true)
    private HealthStatus healthStatus;
    @ApiModelProperty(value = "Group violations", required = true)
    private Violations violations;
    @ApiModelProperty(value = "Project in group count", example = "42", required = true)
    private Integer projectCount;
    private List<GroupOverview> groups;

}
