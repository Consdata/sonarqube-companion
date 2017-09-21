package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Project {

    public static String getProjectUniqueId(final String serverId, final String projectKey) {
        return String.format("%s$%s", serverId, projectKey);
    }

    private String serverId;
    private String key;
    private String name;
    private String serverUrl;

    public Project withServerUrl(final String serverUrl) {
        return Project
                .builder()
                .serverId(serverId)
                .key(key)
                .name(name)
                .serverUrl(serverUrl)
                .build();
    }

    public String getId() {
        return getProjectUniqueId(serverId, key);
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
