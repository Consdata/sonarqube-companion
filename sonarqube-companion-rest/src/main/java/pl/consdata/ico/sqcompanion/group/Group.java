package pl.consdata.ico.sqcompanion.group;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.project.Project;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Group {

    private final String uuid;
    private final String name;
    private final List<Group> groups;
    private final List<Project> projects;

    public static Group fromDefinition(GroupDefinition definition) {
        return Group
                .builder()
                .uuid(definition.getUuid())
                .name(definition.getName())
                .groups(definition.getGroups().stream().map(Group::fromDefinition).collect(Collectors.toList()))
                .build();
    }

    public void accept(final GroupVisitor visitor) {
        visitor.visit(this);
        groups.forEach(group -> group.accept(visitor));
    }

}
