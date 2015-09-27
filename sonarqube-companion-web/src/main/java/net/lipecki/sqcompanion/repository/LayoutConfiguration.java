package net.lipecki.sqcompanion.repository;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class LayoutConfiguration {

    private List<GroupConfiguration> groups = new ArrayList<>();
    private List<ProjectConfiguration> projects = new ArrayList<>();

    public static LayoutConfiguration fromJson(final String json) {
        return new Gson().fromJson(json, LayoutConfiguration.class);
    }

    public List<ProjectConfiguration> getProjects() {
        return projects;
    }

    public List<GroupConfiguration> getGroups() {
        return groups;
    }

}
