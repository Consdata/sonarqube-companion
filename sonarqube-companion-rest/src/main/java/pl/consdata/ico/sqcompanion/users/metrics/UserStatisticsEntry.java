package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class UserStatisticsEntry {
    private LocalDate from;
    private LocalDate to;
    private String user;
    private String projectKey;
    private Long blockers;
    private Long criticals;
    private Long majors;
    private Long minors;
    private Long info;


    public static UserStatisticsEntry fromEntity(UserStatisticsEntryEntity entity) {
        return UserStatisticsEntry.builder()
                .user(entity.getUser())
                .from(entity.getBegin())
                .to(entity.getEnd())
                .projectKey(entity.getProjectKey())
                .blockers(entity.getBlockers())
                .criticals(entity.getCriticals())
                .majors(entity.getMajors())
                .minors(entity.getMinors())
                .info(entity.getInfos()).build();
    }

    public static UserStatisticsEntry empty() {
        return UserStatisticsEntry.builder()
                .blockers(0L)
                .criticals(0L)
                .majors(0L)
                .minors(0L)
                .info(0L).build();
    }
}
