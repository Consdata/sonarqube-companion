package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AppConfig {

    @Singular
    private List<ServerDefinition> servers;

    private GroupDefinition rootGroup;

    private SchedulerConfig scheduler;

    public ServerDefinition getServer(final String serverId) {
        return servers
                .stream()
                .filter(s -> s.getId().equals(serverId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find server for id: " + serverId));
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
}
