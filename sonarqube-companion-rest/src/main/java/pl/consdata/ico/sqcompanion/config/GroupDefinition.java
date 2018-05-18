package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.consdata.ico.sqcompanion.statistics.StatisticConfig;
import pl.consdata.ico.sqcompanion.widget.Widget;

import java.util.ArrayList;
import java.util.Collections;
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
    @Singular
    private List<GroupEvent> events;
    @Singular
    private List<WebhookDefinition> webhooks;
    @Singular
    private List<Widget> widgets;
    @Singular
    private List<StatisticConfig> statistics;

    public List<GroupDefinition> getGroups() {
        return groups != null ? groups : new ArrayList<>();
    }

    public List<ProjectLink> getProjectLinks() {
        return projectLinks != null ? projectLinks : new ArrayList<>();
    }

    public List<WebhookDefinition> getWebhooks() {
        return webhooks != null ? webhooks : new ArrayList<>();
    }

    public List<StatisticConfig> getStatistics() {
        return statistics == null ? Collections.emptyList() : statistics;
    }

    public List<Widget> getWidgets() {
        return widgets == null ? Collections.emptyList() : widgets;
    }

}
