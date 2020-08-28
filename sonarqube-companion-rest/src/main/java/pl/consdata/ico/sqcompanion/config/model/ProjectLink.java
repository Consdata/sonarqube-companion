package pl.consdata.ico.sqcompanion.config.model;

import lombok.*;

import java.util.Map;

/**
 * Możliwości rozwoju:
 * - obsługa exclude w ramach pola additional
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectLink {

    private String uuid;
    private String serverId;
    private ProjectLinkType type;
    @Singular("config")
    private Map<String, Object> config;

}
