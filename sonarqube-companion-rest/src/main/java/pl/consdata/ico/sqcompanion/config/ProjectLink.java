package pl.consdata.ico.sqcompanion.config;

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
    @Singular("config")
    private Map<String, Object> config;

}
