package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectLink {

    private final String serverId;
    private final ProjectLinkType type;
    private final String link;
    // TODO: dodatkowo dla regex exclude?

}
