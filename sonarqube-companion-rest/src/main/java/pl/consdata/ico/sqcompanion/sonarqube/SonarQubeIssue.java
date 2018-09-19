package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

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
    final Date updateDate;
    final String message;

    public LocalDate getCreationDay() {
        return creationDate != null ? LocalDateUtil.asLocalDate(creationDate) : null;
    }

    public LocalDate getUpdateDay() {
        return updateDate != null ? LocalDateUtil.asLocalDate(updateDate) : null;
    }

}
