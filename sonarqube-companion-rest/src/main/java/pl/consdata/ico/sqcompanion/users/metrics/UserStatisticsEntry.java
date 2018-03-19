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
public class UserStatisticsEntry {
    @Id
    private String id;
    private String user;
    private String projectKey;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;

    public static String combineId(final String serverId, final String projectKey, final String user, final LocalDate date) {
        return String.format(
                "%s$%s$%s$%s",
                serverId,
                projectKey,
                user,
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    public static UserStatisticsEntry empty(String user) {
        return UserStatisticsEntry
                .builder()
                .user(user)
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0)
                .build();
    }

}