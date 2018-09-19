package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class SonarQubeIssue {

    final SonarQubeIssueSeverity severity;
    final String key;
    final SonarQubeIssueStatus status;
    final String author;
    final Date creationDate;
    final LocalDate creationDay;
    final Date updateDate;
    final LocalDate updateDay;
    final String message;

}
