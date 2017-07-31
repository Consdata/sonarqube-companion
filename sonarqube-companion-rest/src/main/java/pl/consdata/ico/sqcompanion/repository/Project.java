package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Project project = (Project) o;
        return Objects.equals(serverId, project.serverId) && Objects.equals(key, project.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverId, key);
    }

}
