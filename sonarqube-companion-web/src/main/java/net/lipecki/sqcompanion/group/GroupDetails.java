package net.lipecki.sqcompanion.group;

import com.google.common.base.Objects;
import net.lipecki.sqcompanion.project.ProjectSummary;

import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class GroupDetails {

    private String id;

    private String name;

    private Integer blockers;

    private Integer criticals;

    private Integer otherIssues;

    private StatusCode status;

    private List<ProjectSummary> projects;

    private List<Issue> issues;

    private Integer healthyStreak;

    private List<IssuesHistoryPoint> historicalData;

    public GroupDetails() {
    }

    public GroupDetails(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public List<IssuesHistoryPoint> getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(final List<IssuesHistoryPoint> historicalData) {
        this.historicalData = historicalData;
    }

    public Integer getHealthyStreak() {
        return healthyStreak;
    }

    public void setHealthyStreak(final Integer healthyStreak) {
        this.healthyStreak = healthyStreak;
    }

    public void setOtherIssues(final Integer otherIssues) {
        this.otherIssues = otherIssues;
    }

    public List<ProjectSummary> getProjects() {
        return projects;
    }

    public void setProjects(final List<ProjectSummary> projects) {
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
        final GroupDetails that = (GroupDetails) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static GroupDetails of(final Group group) {
        return new GroupDetails(group.getId(), group.getName());
    }


    public void setOtherIssues(final int otherIssues) {
        this.otherIssues = otherIssues;
    }

    public int getOtherIssues() {
        return otherIssues;
    }

    public String getStatusName() {
        return String.valueOf(status).toLowerCase();
    }

    public Integer getStatusCode() {
        return this.status.getCode();
    }

    public void setStatus(final StatusCode status) {
        this.status = status;
    }

    public StatusCode getStatus() {
        return status;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(final List<Issue> issues) {
        this.issues = issues;
    }
}
