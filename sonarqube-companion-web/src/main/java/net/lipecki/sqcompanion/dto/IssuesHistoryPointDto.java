package net.lipecki.sqcompanion.dto;

import java.util.Date;

/**
 * Created by gregorry on 26.09.2015.
 */
public class IssuesHistoryPointDto {

    private Date date;

    private Integer blockers;

    private Integer criticals;

    public IssuesHistoryPointDto() {
    }

    public IssuesHistoryPointDto(final Date date, final Integer blockers, final Integer criticals) {
        this.date = date;
        this.blockers = blockers;
        this.criticals = criticals;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
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
}
