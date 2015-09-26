package net.lipecki.sqcompanion.sonarqube.issue;

import com.google.common.base.MoreObjects;

import java.util.Date;

/**
 * Created by gregorry on 26.09.2015.
 */
public class SonarQubeIssuesIssueResultDto {

    private String key;

    private String project;

    private String status;

    private String severity;

    private String component;

    private Integer line;

    private String message;

    private String rule;

    private Date creationDate;

    private Date updateDate;

    private String debt;

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getProject() {
        return project;
    }

    public void setProject(final String project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(final String severity) {
        this.severity = severity;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        this.component = component;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(final Integer line) {
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(final String rule) {
        this.rule = rule;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(final String debt) {
        this.debt = debt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .add("project", project)
                .add("status", status)
                .add("severity", severity)
                .add("component", component)
                .add("line", line)
                .add("message", message)
                .add("rule", rule)
                .add("creationDate", creationDate)
                .add("updateDate", updateDate)
                .add("debt", debt)
                .toString();
    }

}
