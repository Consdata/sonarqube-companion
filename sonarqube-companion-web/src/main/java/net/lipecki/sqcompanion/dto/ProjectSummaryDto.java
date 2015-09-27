package net.lipecki.sqcompanion.dto;

/**
 * Created by gregorry on 26.09.2015.
 */
public class ProjectSummaryDto {

    private String id;

    private String name;

    private Integer blockers;

    private Integer criticals;

    private StatusCodeDto status;

    public ProjectSummaryDto() {
    }

    public ProjectSummaryDto(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectSummaryDto(final String id, final String name, final Integer blockers, final Integer criticals, final StatusCodeDto status) {
        this.id = id;
        this.name = name;
        this.blockers = blockers;
        this.criticals = criticals;
        this.status = status;
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

    public StatusCodeDto getStatus() {
        return status;
    }

    public void setStatus(final StatusCodeDto status) {
        this.status = status;
    }

    public String getStatusName() {
        return String.valueOf(status).toLowerCase();
    }

    public Integer getStatusCode() {
        return this.status.getCode();
    }

}
