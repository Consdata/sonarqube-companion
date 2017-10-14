package pl.consdata.ico.sqcompanion.demo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
public class DemoDefinition {

    public Optional<DemoServer> getServer(final String serverId) {
        return Optional.ofNullable(servers.get(serverId));
    }

    private Map<String, DemoServer> servers;

}
