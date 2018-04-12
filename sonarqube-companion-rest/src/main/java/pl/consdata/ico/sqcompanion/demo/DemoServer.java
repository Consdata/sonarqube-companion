package pl.consdata.ico.sqcompanion.demo;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class DemoServer {

    public Optional<DemoProject> getProject(final String projectKey) {
        return projects.stream().filter(p -> p.getKey().equals(projectKey)).findFirst();
    }

    private List<DemoProject> projects;

}
