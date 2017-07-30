package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ServerAuthentication {

    private String type;
    private Map<String, String> params;

}
