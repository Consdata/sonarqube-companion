package pl.consdata.ico.sqcompanion.config.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"groups", "projectLinks", "events", "webhooks", "members"})
public class GroupDefinition {
    private String uuid;
    private String name;
    private String description;
    @Singular
    private List<GroupDefinition> groups;
    @Singular
    private List<ProjectLink> projectLinks;
    @Singular
    private List<GroupEvent> events;
    @Singular
    private List<WebhookDefinition> webhooks;
    @Singular
    private List<String> members;

}
