package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class SonarQubeIssue {

    private SonarQubeIssueSeverity severity;
    private String key;
    private SonarQubeIssueStatus status;
    private String author;
    private Date creationDate;
    private Date updateDate;
    private String message;

    public LocalDate getCreationDay() {
        return creationDate != null ? LocalDateUtil.asLocalDate(creationDate) : null;
    }

    public LocalDate getUpdateDay() {
        return updateDate != null ? LocalDateUtil.asLocalDate(updateDate) : null;
    }

}
