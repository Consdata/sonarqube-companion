package net.lipecki.sqcompanion.group;

import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesIssueResultDto;

import java.util.Date;

/**
 * Created by gregorry on 26.09.2015.
 */
public class Issue {

    private final String key;
    private final String project;
    private final String component;
    private final String message;
    private final String severity;
    private final Date creationDate;

    public Issue(final String key, final String project, final String component, final String message, final String severity, final Date creationDate) {
        this.key = key;
        this.project = project;
        this.component = component;
        this.message = message;
        this.severity = severity;
        this.creationDate = creationDate;
    }

    public static Issue of(final SonarQubeIssuesIssueResultDto dto) {
        return new Issue(dto.getKey(), dto.getProject(), dto.getComponent(), dto.getMessage(), dto.getSeverity(), dto
                .getCreationDate());
    }

    public String getKey() {
        return key;
    }

    public String getProject() {
        return project;
    }

    public String getComponent() {
        return component;
    }

    public String getMessage() {
        return message;
    }

    public String getSeverity() {
        return severity;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
