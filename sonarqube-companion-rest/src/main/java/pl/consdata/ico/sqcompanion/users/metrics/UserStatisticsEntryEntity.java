package pl.consdata.ico.sqcompanion.users.metrics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity(name = "user_statistics_entries")
@Table(
        indexes = {
                @Index(name = "IDX_USER_STATISTICS_ENTRIES_USER", columnList = "user"),
                @Index(name = "IDX_USER_STATISTICS_ENTRIES_DATE", columnList = "date"),
        }
)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserStatisticsEntryEntity {
    @Id
    private String id;
    private String user;
    private String serverId;
    private String projectKey;
    private LocalDate date;
    private Long blockers;
    private Long criticals;
    private Long majors;
    private Long minors;
    private Long infos;

    public static String combineId(final String serverId, final String projectKey, final String user, final String date) {
        return String.format(
                "%s$%s$%s$%s",
                serverId,
                projectKey,
                user,
                date
        );
    }
}