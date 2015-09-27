package net.lipecki.sqcompanion.repository;

import java.time.LocalDate;

/**
 * Created by gregorry on 26.09.2015.
 */
public class HistoryPoint {

    public static HistoryPoint sum(HistoryPoint original, HistoryPoint other) {
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

    private Integer blockers = 0;

    private Integer criticals = 0;

    private Integer majors = 0;

    private Integer minors = 0;

    private Integer infos = 0;

    private final LocalDate date;

    public HistoryPoint(final LocalDate date) {
        this.date = date;
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
}
