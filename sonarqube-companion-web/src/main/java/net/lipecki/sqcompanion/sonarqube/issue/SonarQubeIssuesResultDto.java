package net.lipecki.sqcompanion.sonarqube.issue;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class SonarQubeIssuesResultDto {

    private Integer total;

    private SonarQubeIssuesProjectResultDto[] projects;

    private SonarQubeIssuesComponentResultDto[] components;

    private SonarQubeIssuesIssueResultDto[] issues;

    private SonarQubeIssuesRuleResultDto[] rules;

    public SonarQubeIssuesResultDto() {
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public SonarQubeIssuesProjectResultDto[] getProjects() {
        return projects;
    }

    public void setProjects(final SonarQubeIssuesProjectResultDto[] projects) {
        this.projects = projects;
    }

    public SonarQubeIssuesComponentResultDto[] getComponents() {
        return components;
    }

    public void setComponents(final SonarQubeIssuesComponentResultDto[] components) {
        this.components = components;
    }

    public SonarQubeIssuesIssueResultDto[] getIssues() {
        return issues;
    }

    public void setIssues(final SonarQubeIssuesIssueResultDto[] issues) {
        this.issues = issues;
    }

    public SonarQubeIssuesRuleResultDto[] getRules() {
        return rules;
    }

    public void setRules(final SonarQubeIssuesRuleResultDto[] rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("total", total)
                .add("projects", projects)
                .add("components", components)
                .add("issues", issues)
                .add("rules", rules)
                .toString();
    }

}
