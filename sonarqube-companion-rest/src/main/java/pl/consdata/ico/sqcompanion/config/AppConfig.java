package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class AppConfig {

    @Singular
    private List<ServerDefinition> servers;

    private GroupDefinition rootGroup;

}
