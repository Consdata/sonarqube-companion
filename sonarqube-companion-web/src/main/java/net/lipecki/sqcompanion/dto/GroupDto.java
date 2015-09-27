package net.lipecki.sqcompanion.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupDto {

    private final String id;

    private final String name;

    private final List<String> projects;

    public GroupDto(final String id, final String name, final String... projects) {
        this.id = id;
        this.name = name;
        this.projects = Arrays.asList(projects);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getProjects() {
        return projects;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupDto groupDto = (GroupDto) o;
        return Objects.equal(id, groupDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("projects", projects)
                .toString();
    }

}
