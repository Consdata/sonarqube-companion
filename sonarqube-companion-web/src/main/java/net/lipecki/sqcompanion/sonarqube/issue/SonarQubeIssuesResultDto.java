package net.lipecki.sqcompanion.sonarqube.issue;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class SonarQubeIssuesResultDto {

    private Integer total;

    private Integer p;

    private Integer ps;

    private SonarQubeIssuesPagingResultDto paging = new SonarQubeIssuesPagingResultDto();

    private SonarQubeIssuesProjectResultDto[] projects = new SonarQubeIssuesProjectResultDto[0];

    private SonarQubeIssuesComponentResultDto[] components = new SonarQubeIssuesComponentResultDto[0];

    private SonarQubeIssuesIssueResultDto[] issues = new SonarQubeIssuesIssueResultDto[0];

    private SonarQubeIssuesRuleResultDto[] rules = new SonarQubeIssuesRuleResultDto[0];

    public SonarQubeIssuesResultDto() {
    }

    public Integer getP() {
        return p;
    }

    public void setP(final Integer p) {
        this.p = p;
    }

    public Integer getPs() {
        return ps;
    }

    public void setPs(final Integer ps) {
        this.ps = ps;
    }

    public SonarQubeIssuesPagingResultDto getPaging() {
        return paging;
    }

    public void setPaging(final SonarQubeIssuesPagingResultDto paging) {
        this.paging = paging;
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
