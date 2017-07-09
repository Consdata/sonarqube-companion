package net.lipecki.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * Możliwości rozwoju:
 * - obsługa exclude w ramach pola additional
 */
@Data
@Builder
public class ProjectLink {

    private String serverId;
    private ProjectLinkType type;
    private String link;
    @Singular
    private Map<String, Object> additionals;

}
