package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.repository.Group;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupLightModel {
    private String name;
    private String uuid;
    private List<GroupLightModel> groups;

    public static GroupLightModel of(GroupDefinition group) {
        return GroupLightModel.builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .groups(ofNullable(group.getGroups()).orElse(emptyList()).stream().map(GroupLightModel::of).collect(Collectors.toList()))
                .build();
    }

    public static GroupLightModel of(Group group) {
        return GroupLightModel.builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .groups(ofNullable(group.getGroups()).orElse(emptyList()).stream().map(GroupLightModel::of).collect(Collectors.toList()))
                .build();
    }

}
