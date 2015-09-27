package net.lipecki.sqcompanion.repository;

/**
 * Created by gregorry on 26.09.2015.
 */
public class ProjectConfiguration {

    private String name;
    private String key;
    private String sonarqubeKey;

    public ProjectConfiguration() {
    }

    public ProjectConfiguration(final String name, final String key, final String sonarqubeKey) {
        this.name = name;
        this.key = key;
        this.sonarqubeKey = sonarqubeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getSonarqubeKey() {
        return sonarqubeKey;
    }

    public void setSonarqubeKey(final String sonarqubeKey) {
        this.sonarqubeKey = sonarqubeKey;
    }
}
