package net.lipecki.sqcompanion.group;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupSummary {

    private String id;

    private String name;

    private StatusCode status;

    private Integer blockers;

    private Integer criticals;

    public GroupSummary() {
    }

    public GroupSummary(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public GroupSummary(final String id, final String name, final StatusCode status, final Integer blockers, final Integer criticals) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.blockers = blockers;
        this.criticals = criticals;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public StatusCode getStatus() {
        return status;
    }

    public void setStatus(final StatusCode status) {
        this.status = status;
    }

    public String getStatusName() {
        return String.valueOf(status).toLowerCase();
    }

    public Integer getStatusCode() {
        return this.status.getCode();
    }

    public Integer getBlockers() {
        return blockers;
    }

    public void setBlockers(final Integer blockers) {
        this.blockers = blockers;
    }

    public Integer getCriticals() {
        return criticals;
    }

    public void setCriticals(final Integer criticals) {
        this.criticals = criticals;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupSummary that = (GroupSummary) o;
        return Objects.equal(id, that.id);
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
                .add("status", status)
                .add("blockers", blockers)
                .add("criticals", criticals)
                .toString();
    }

    public static GroupSummary of(final Group group) {
        return new GroupSummary(group.getId(), group.getName());
    }
}
