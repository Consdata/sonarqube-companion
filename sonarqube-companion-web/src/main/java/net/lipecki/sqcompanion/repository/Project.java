package net.lipecki.sqcompanion.repository;

/**
 * Created by gregorry on 26.09.2015.
 */
public class Project {

    private final String key;
    private final String name;
    private final String sonarqubeKey;
    private final String description;
    private Issues issues = new Issues();
    private History history = new History();

    public Project(final String key, final String name, final String sonarqubeKey, final String description) {
        this.key = key;
        this.name = name;
        this.sonarqubeKey = sonarqubeKey;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSonarqubeKey() {
        return sonarqubeKey;
    }

    public String getDescription() {
        return description;
    }

    public HealthStatus getHealthStatus() {
        return HealthStatus.of(issues);
    }

    public Issues getIssues() {
        return issues;
    }

    void setIssues(final Issues issues) {
        this.issues = issues;
    }

    public History getHistory() {
        return history;
    }

    void setHistory(final History history) {
        this.history = history;
    }

}
