package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SonarQubeIssue {

    private final SonarQubeIssueSeverity severity;
    private final String key;
    private final SonarQubeIssueStatus status;
    private final String author;
    private final Date creationDate;
    private final Date updateDate;
    private final String message;
    private final String project;

}
