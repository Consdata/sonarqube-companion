package net.lipecki.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProjectLink {

    private final String serverId;
    private final ProjectLinkType type;
    private final String link;
    // TODO: obsługa np exclude
    private final Map<String, Object> additional;

}
