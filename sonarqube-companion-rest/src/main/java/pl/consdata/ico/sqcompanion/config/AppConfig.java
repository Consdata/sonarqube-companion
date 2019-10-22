package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.springframework.util.CollectionUtils;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.SchedulerConfig;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Data
@Builder
public class AppConfig {

    @Singular
    private List<ServerDefinition> servers;

    private GroupDefinition rootGroup;

    private SchedulerConfig scheduler;

    public ServerDefinition getServer(final String uuid) {
        return servers
                .stream()
                .filter(s -> s.getUuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find server for uuid: " + uuid));
    }

    public List<ServerDefinition> getServers() {
        return servers != null ? servers : new ArrayList<>();
    }

    public GroupDefinition getRootGroup() {
        return rootGroup;
    }

    public SchedulerConfig getScheduler() {
        return scheduler != null ? scheduler : SchedulerConfig.getDefault();
    }

    private GroupDefinition getGroup(String uuid, List<GroupDefinition> groups) {
        for (GroupDefinition group : groups) {
            if (group.getUuid().equals(uuid)) {
                return group;
            } else {
                GroupDefinition definition = getGroup(uuid, ofNullable(group.getGroups()).orElse(emptyList()));
                if (definition != null) {
                    return definition;
                }
            }
        }
        return null;
    }

    private GroupDefinition getGroupParent(String uuid, List<GroupDefinition> groups) {
        for (GroupDefinition group : groups) {
            if (CollectionUtils.isEmpty(group.getGroups())) {
                return null;
            }
            if (group.getGroups().stream().anyMatch(g -> g.getUuid().equals(uuid))) {
                return group;
            } else {
                GroupDefinition definition = getGroupParent(uuid, ofNullable(group.getGroups()).orElse(emptyList()));
                if (definition != null) {
                    return definition;
                }
            }
        }
        return null;
    }

    public GroupDefinition getGroup(String uuid, GroupDefinition groupDefinition) {
        return getGroup(uuid, Collections.singletonList(groupDefinition));
    }

    public GroupDefinition getGroup(String uuid) {
        return getGroup(uuid, Collections.singletonList(getRootGroup()));
    }

    public GroupDefinition getGroupParent(String uuid) {
        return getGroupParent(uuid, Collections.singletonList(getRootGroup()));
    }

}
