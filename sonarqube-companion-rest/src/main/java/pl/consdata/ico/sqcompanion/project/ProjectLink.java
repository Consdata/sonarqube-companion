package pl.consdata.ico.sqcompanion.project;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.server.ServerDefinition;

@Data
@Builder
public class ProjectLink {

    private final ServerDefinition server;
    private final ProjectLinkType type;
    private final String link;

}
