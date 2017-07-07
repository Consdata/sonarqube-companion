package net.lipecki.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Możliwości rozwoju:
 * - obsługa exclude w ramach pola additional
 */
@Data
@Builder
public class ProjectLink {

    private final String serverId;
    private final ProjectLinkType type;
    private final String link;
    private final Map<String, Object> additional;

}
