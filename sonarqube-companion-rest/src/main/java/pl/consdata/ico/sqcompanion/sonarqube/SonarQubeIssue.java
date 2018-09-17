package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SonarQubeIssue {

    final SonarQubeIssueSeverity severity;
    final String key;
    final SonarQubeIssueStatus status;
    final String author;
    final Date creationDate;
    final Date updateDate;
    final String message;

}
