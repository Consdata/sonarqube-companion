package pl.consdata.ico.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.consdata.ico.sqcompanion.project.ProjectLink;

import java.util.List;

@Data
@Builder
public class GroupDefinition {

    private final String uuid;
    private final String name;
    private final String description;

    @Singular
    private final List<GroupDefinition> groups;

    @Singular
    private final List<ProjectLink> projectLinks;

}
