package net.lipecki.sqcompanion.repository;

import java.time.LocalDate;

/**
 * Created by gregorry on 26.09.2015.
 */
public class HistoryPoint {

    private final LocalDate date;
    private Integer blockers = 0;
    private Integer criticals = 0;
    private Integer majors = 0;
    private Integer minors = 0;
    private Integer infos = 0;
    public HistoryPoint(final LocalDate date) {
        this.date = date;
    }

    public HistoryPoint(final LocalDate date, final Integer blockers, final Integer criticals, final Integer majors, final Integer minors, final Integer infos) {
        this.date = date;
        this.blockers = blockers;
        this.criticals = criticals;
        this.majors = majors;
        this.minors = minors;
        this.infos = infos;
    }

    public static HistoryPoint merge(HistoryPoint original, HistoryPoint other) {
        final HistoryPoint point = new HistoryPoint(original.getDate());

        point.addBlockers(original.getBlockers());
        point.addBlockers(other.getBlockers());

        point.addCriticals(original.getCriticals());
        point.addCriticals(other.getCriticals());

        point.addMajors(original.getMajors());
        point.addMajors(other.getMajors());

        point.addMinors(original.getMinors());
        point.addMinors(other.getMinors());

        point.addInfos(original.getInfos());
        point.addInfos(other.getInfos());

        return point;
    }

    public Integer getBlockers() {
        return blockers;
    }

    public void addBlockers(final Integer count) {
        blockers += count;
    }

    public Integer getCriticals() {
        return criticals;
    }

    public void addCriticals(final Integer count) {
        criticals += count;
    }

    public Integer getMajors() {
        return majors;
    }

    public void addMajors(final Integer count) {
        majors += count;
    }

    public Integer getMinors() {
        return minors;
    }

    public void addMinors(final Integer count) {
        minors += count;
    }

    public Integer getInfos() {
        return infos;
    }

    public void addInfos(final Integer count) {
        infos += count;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getAll() {
        return getBlockers() + getCriticals() + getMajors() + getMinors() + getInfos();
    }

    public Integer getSignificant() {
        return (IssueSeverity.BLOCKER.isSignificant() ? getBlockers() : 0)
                + (IssueSeverity.CRITICAL.isSignificant() ? getCriticals() : 0)
                + (IssueSeverity.MAJOR.isSignificant() ? getMajors() : 0)
                + (IssueSeverity.MINOR.isSignificant() ? getMinors() : 0)
                + (IssueSeverity.INFO.isSignificant() ? getInfos() : 0);
    }

    public Integer getNonsignificant() {
        return (!IssueSeverity.BLOCKER.isSignificant() ? getBlockers() : 0)
                + (!IssueSeverity.CRITICAL.isSignificant() ? getCriticals() : 0)
                + (!IssueSeverity.MAJOR.isSignificant() ? getMajors() : 0)
                + (!IssueSeverity.MINOR.isSignificant() ? getMinors() : 0)
                + (!IssueSeverity.INFO.isSignificant() ? getInfos() : 0);
    }

    public void addOtherPoint(final HistoryPoint other) {
        addBlockers(other.getBlockers());
        addCriticals(other.getCriticals());
        addMajors(other.getMajors());
        addMinors(other.getMinors());
        addInfos(other.getInfos());
    }
}
