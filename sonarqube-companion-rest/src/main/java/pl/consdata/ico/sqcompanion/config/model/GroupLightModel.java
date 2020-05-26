package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.repository.Group;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupLightModel {
    private String name;
    private String uuid;

    public static GroupLightModel of(GroupDefinition group) {
        return GroupLightModel.builder()
                .uuid(group.getUuid())
                .name(group.getName())
                .build();
    }
}
