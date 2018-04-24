package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.config.GroupEvent;
import pl.consdata.ico.sqcompanion.widget.Widget;
import pl.consdata.ico.sqcompanion.statistics.StatisticConfig;

import java.util.*;

@Data
@Builder
public class Group {

    private String uuid;
    private String name;
    private List<Group> groups;
    private List<Project> projects;
    private List<GroupEvent> events;
    private List<StatisticConfig> statistics;
    private List<Widget> widgets;

    public void accept(final GroupVisitor visitor) {
        visitor.visit(this);
        groups.forEach(gr -> gr.accept(visitor));
    }

    public List<Project> getAllProjects() {
        final Set<Project> result = new HashSet<>();
        accept(gr -> result.addAll(gr.getProjects()));
        return new ArrayList<>(result);
    }

    public List<Group> getAllGroups() {
        final List<Group> result = new ArrayList<>();
        accept(result::add);
        return result;
    }

    public Optional<Project> getProject(final String projectKey) {
        return getAllProjects().stream().filter(project -> Objects.equals(project.getKey(), projectKey)).findFirst();
    }

    public List<Group> getGroups() {
        return groups != null ? groups : new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects != null ? projects : new ArrayList<>();
    }

    @FunctionalInterface
    public interface GroupVisitor {

        void visit(final Group group);

    }

}
