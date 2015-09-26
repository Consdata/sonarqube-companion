package net.lipecki.sqcompanion.project;

import net.lipecki.sqcompanion.group.StatusCode;

/**
 * Created by gregorry on 26.09.2015.
 */
public class ProjectSummary {

    private String id;

    private String name;

    private Integer blockers;

    private Integer criticals;

    private StatusCode status;

    public ProjectSummary() {
    }

    public ProjectSummary(final String id, final String name) {
        this.id = id;
        this.name = name;
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

}
