package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerAuthentication {

    private String type;
    private Map<String, String> params;

}
