package pl.consdata.ico.sqcompanion.violation.user.diff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "user_project_diff_history_entries")
@Table(
        indexes = {
                @Index(name = "IDX_USER_DIFF_HISTORY_ENTRIES_USER_ID", columnList = "userId"),
                @Index(name = "IDX_USER_DIFF_HISTORY_ENTRIES_USER_ID_PROJECT_KEY", columnList = "userId,projectKey"),
                @Index(name = "IDX_USER_DIFF_HISTORY_ENTRIES_USER_ID_PROJECT_KEY_DATE", columnList = "userId,projectKey,date")
        }
)
@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class UserProjectViolationDiffHistoryEntry {

    @Id
    private String id;
    private String serverId;
    private String projectKey;
    private String userId;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;
    private LocalDate date;
    @Column(columnDefinition = "VARCHAR(MAX)")
    private String issues;

    public static String combineId(final String serverId, final String userId, final String projectKey, final LocalDate date) {
        return String.format(
                "%s$%s$%s$%s",
                serverId,
                userId,
                projectKey,
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

}
