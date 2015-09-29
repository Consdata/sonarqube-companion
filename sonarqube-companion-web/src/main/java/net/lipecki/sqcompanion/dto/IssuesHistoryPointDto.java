package net.lipecki.sqcompanion.dto;

import java.util.Date;

/**
 * Created by gregorry on 26.09.2015.
 */
public class IssuesHistoryPointDto {

    private final Date date;
    private final Integer blockers;
    private final Integer criticals;
    private final Integer majors;
    private final Integer minors;
    private final Integer infos;
    private final Integer all;
    private final Integer significant;
    private final Integer nonsignificant;

    public IssuesHistoryPointDto(final Date date, final Integer blockers, final Integer criticals, final Integer
            majors, final Integer minors, final Integer infos, final Integer all, final Integer significant, final Integer nonsignificant) {
        this.date = date;
        this.blockers = blockers;
        this.criticals = criticals;
        this.majors = majors;
        this.minors = minors;
        this.infos = infos;
        this.all = all;
        this.significant = significant;
        this.nonsignificant = nonsignificant;
    }

    public Date getDate() {
        return date;
    }

    public Integer getBlockers() {
        return blockers;
    }


    public Integer getCriticals() {
        return criticals;
    }

    public Integer getMajors() {
        return majors;
    }

    public Integer getMinors() {
        return minors;
    }

    public Integer getInfos() {
        return infos;
    }

    public Integer getAll() {
        return all;
    }

    public Integer getSignificant() {
        return significant;
    }

    public Integer getNonsignificant() {
        return nonsignificant;
    }
}
