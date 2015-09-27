package net.lipecki.sqcompanion.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class Group {

    private String key;
    private String name;
    private String description;
    private Issues issues = new Issues();
    private History history = new History();
    private List<Project> projects = new ArrayList<>();

    public Group(final String key, final String name, final String description) {
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HealthStatus getHealthStatus() {
        return HealthStatus.of(issues);
    }

    public void setProjects(final List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setIssues(final Issues issues) {
        this.issues = issues;
    }

    public Issues getIssues() {
        return issues;
    }

    public void setHistory(final History history) {
        this.history = history;
    }

    public History getHistory() {
        return history;
    }

}
