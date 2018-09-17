package pl.consdata.ico.sqcompanion.violation.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "user_project_history_entries")
@Table(
        indexes = {
                @Index(name = "IDX_USER_HISTORY_ENTRIES_USER_ID", columnList = "userId"),
                @Index(name = "IDX_USER_HISTORY_ENTRIES_USER_ID_PROJECT_KEY", columnList = "userId,projectKey"),
                @Index(name = "IDX_USER_HISTORY_ENTRIES_USER_ID_PROJECT_KEY_DATE", columnList = "userId,projectKey,date")
        }
)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserProjectHistoryEntryEntity {

    public static String combineId(final String serverId, final String userId, final String projectKey, final LocalDate date) {
        return String.format(
                "%s$%s$%s$%s",
                serverId,
                userId,
                projectKey,
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

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

}
