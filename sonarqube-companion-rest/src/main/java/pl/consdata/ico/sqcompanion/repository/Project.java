package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    private String serverId;
    private String key;
    private String name;

}
