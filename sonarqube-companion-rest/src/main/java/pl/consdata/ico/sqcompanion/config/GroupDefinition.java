package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GroupDefinition {

    private String uuid;
    private String name;
    private String description;
    @Singular
    private List<GroupDefinition> groups;
    @Singular
    private List<ProjectLink> projectLinks;

    public List<GroupDefinition> getGroups() {
        return groups != null ? groups : new ArrayList<>();
    }

    public List<ProjectLink> getProjectLinks() {
        return projectLinks != null ? projectLinks : new ArrayList<>();
    }



}
