package net.lipecki.sqcompanion.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupConfiguration {

    private String key;

    private String name;

    private List<String> projects = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(final List<String> projects) {
        this.projects = projects;
    }

}
