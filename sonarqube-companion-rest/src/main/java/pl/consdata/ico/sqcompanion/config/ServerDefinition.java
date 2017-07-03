package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServerDefinition {

    private String id;

    private String url;

}
