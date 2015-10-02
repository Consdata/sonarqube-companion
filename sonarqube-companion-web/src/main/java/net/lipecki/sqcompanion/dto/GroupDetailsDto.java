package net.lipecki.sqcompanion.dto;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupDetailsDto {

    private String id;
    private String name;
    private Integer blockers;
    private Integer criticals;
    private Integer otherIssues;
    private StatusCodeDto status;
    private List<ProjectSummaryDto> projects = new ArrayList<>();
    private List<IssueDto> issues = new ArrayList<>();
    private Integer healthyStreakSiginificant;
    private Integer healthyStreakAny;
    private List<IssuesHistoryPointDto> historicalData = new ArrayList<>();

    public GroupDetailsDto() {
    }

    public GroupDetailsDto(final String id, final String name, final Integer blockers, final Integer criticals, final Integer otherIssues, final StatusCodeDto status, final List<ProjectSummaryDto> projects, final List<IssueDto> issues, final Integer healthyStreakSignificant, final Integer healthyStreakAny, final List<IssuesHistoryPointDto> historicalData) {
        this.id = id;
        this.name = name;
        this.blockers = blockers;
        this.criticals = criticals;
        this.otherIssues = otherIssues;
        this.status = status;
        this.projects = projects;
        this.issues = issues;
        this.healthyStreakSiginificant = healthyStreakSignificant;
        this.healthyStreakAny = healthyStreakAny;
        this.historicalData = historicalData;
    }

    public List<IssuesHistoryPointDto> getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(final List<IssuesHistoryPointDto> historicalData) {
        this.historicalData = historicalData;
    }

    public Integer getHealthyStreakSiginificant() {
        return healthyStreakSiginificant;
    }

    public void setHealthyStreakSiginificant(final Integer healthyStreakSiginificant) {
        this.healthyStreakSiginificant = healthyStreakSiginificant;
    }

    public void setOtherIssues(final Integer otherIssues) {
        this.otherIssues = otherIssues;
    }

    public List<ProjectSummaryDto> getProjects() {
        return projects;
    }

    public void setProjects(final List<ProjectSummaryDto> projects) {
        this.projects = projects;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GroupDetailsDto that = (GroupDetailsDto) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public int getOtherIssues() {
        return otherIssues;
    }

    public void setOtherIssues(final int otherIssues) {
        this.otherIssues = otherIssues;
    }

    public String getStatusName() {
        return String.valueOf(status).toLowerCase();
    }

    public Integer getStatusCode() {
        return this.status.getCode();
    }

    public StatusCodeDto getStatus() {
        return status;
    }

    public void setStatus(final StatusCodeDto status) {
        this.status = status;
    }

    public List<IssueDto> getIssues() {
        return issues;
    }

    public void setIssues(final List<IssueDto> issues) {
        this.issues = issues;
    }

    public Integer getHealthyStreakAny() {
        return healthyStreakAny;
    }

    public void setHealthyStreakAny(final Integer healthyStreakAny) {
        this.healthyStreakAny = healthyStreakAny;
    }

}
