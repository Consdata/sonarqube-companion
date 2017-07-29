package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Project {

    private String serverId;
    private String key;
    private String name;
    private String url;

    public Project withUrl(final String url) {
        return Project
                .builder()
                .serverId(serverId)
                .key(key)
                .name(name)
                .url(url)
                .build();
    }

}
