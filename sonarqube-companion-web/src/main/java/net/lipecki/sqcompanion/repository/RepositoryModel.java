package net.lipecki.sqcompanion.repository;

import java.util.*;

/**
 * Created by gregorry on 27.09.2015.
 */
public class RepositoryModel {

    private final Date creationDate;
    private final Map<String, Group> groups;
    private final Map<String, Project> projects;

    private RepositoryModel(final Date creationDate, final Map<String, Group> groups, final Map<String, Project>
            projects) {
        this.creationDate = creationDate;
        this.groups = groups;
        this.projects = projects;
    }

    public static RepositoryModel of(final Map<String, Group> groups, final Map<String, Project> projects) {
        return new RepositoryModel(new Date(), groups, projects);
    }

    public List<Group> getGroups() {
        return Collections.unmodifiableList(new ArrayList<>(groups.values()));
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(new ArrayList<>(projects.values()));
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Optional<Group> getGroup(final String id) {
        return Optional.ofNullable(groups.get(id));
    }

    public Optional<Project> getProject(final String id) {
        return Optional.ofNullable(projects.get(id));
    }

}
