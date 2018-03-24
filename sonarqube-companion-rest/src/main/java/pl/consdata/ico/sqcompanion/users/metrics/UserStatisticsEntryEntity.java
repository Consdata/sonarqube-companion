package pl.consdata.ico.sqcompanion.users.metrics;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "user_statistics_entries")
@Table(
        indexes = {
                @Index(name = "IDX_USER_STATISTICS_ENTRIES_USER", columnList = "user"),
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
    /**
     * Inclusive
     */
    private LocalDate begin;
    /**
     * Exclusive
     */
    private LocalDate end;
    private Long blockers;
    private Long criticals;
    private Long majors;
    private Long minors;
    private Long infos;

    public static String combineId(final String serverId, final String projectKey, final String user, final LocalDate from, final LocalDate to) {
        return String.format(
                "%s$%s$%s$%s$%s",
                serverId,
                projectKey,
                user,
                from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }


}