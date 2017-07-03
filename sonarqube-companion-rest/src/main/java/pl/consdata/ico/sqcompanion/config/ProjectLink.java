package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectLink {

    private final ServerDefinition server;
    private final ProjectLinkType type;
    private final String link;

}
