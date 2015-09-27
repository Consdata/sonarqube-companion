package net.lipecki.sqcompanion.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupInfoDto {

    private String name;

    private String id;

    public GroupInfoDto() {
    }

    public GroupInfoDto(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupInfoDto groupInfo = (GroupInfoDto) o;
        return Objects.equal(id, groupInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("id", id)
                .toString();
    }

    public static GroupInfoDto of(final GroupDto groupDto) {
        return new GroupInfoDto(groupDto.getId(), groupDto.getName());
    }

}
