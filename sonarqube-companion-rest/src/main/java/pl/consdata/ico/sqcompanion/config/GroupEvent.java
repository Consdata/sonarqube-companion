package pl.consdata.ico.sqcompanion.config;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

@Data
@Builder
public class GroupEvent {

    private String type;
    private String name;
    private String description;
    @Singular("data")
    private Map<String, Object> data;

}
