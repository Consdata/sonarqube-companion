package pl.consdata.ico.sqcompanion.repository;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.config.GroupEvent;
import pl.consdata.ico.sqcompanion.statistics.StatisticConfig;
import pl.consdata.ico.sqcompanion.widget.Widget;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

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

    public List<StatisticConfig> getAllStatisticConfigsByType(String type) {
        final List<StatisticConfig> result = new ArrayList<>();
        accept(gr -> result.addAll(gr.getStatistics(type)));
        return result;
    }

    public List<Widget> getAllWidgetsByType(Class<?> dependecy) {
        final List<Widget> result = new ArrayList<>();
        accept(gr -> result.addAll(gr.getWidgets(dependecy)));
        return result;
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

    private List<StatisticConfig> getStatistics(String type) {
        return Optional.ofNullable(getStatistics()).orElse(emptyList()).stream().filter(config -> config.getType().equals(type)).collect(Collectors.toList());
    }

    private List<Widget> getWidgets(Class<?> widgetClass) {
        return Optional.ofNullable(getWidgets()).orElse(emptyList()).stream().filter(widgetClass::isInstance).collect(Collectors.toList());
    }


    @FunctionalInterface
    public interface GroupVisitor {

        void visit(final Group group);

    }

}
