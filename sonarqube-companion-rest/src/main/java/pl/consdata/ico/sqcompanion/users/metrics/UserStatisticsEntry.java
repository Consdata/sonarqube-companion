package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Data
@Builder
public class UserStatisticsEntry {
    private LocalDate date;
    private String name;
    private String projectKey;
    private Long blockers;
    private Long criticals;
    private Long majors;
    private Long minors;
    private Long infos;


    public static UserStatisticsEntry fromEntity(UserStatisticsEntryEntity entity) {
        return UserStatisticsEntry.builder()
                .name(entity.getUser())
                .date(entity.getDate())
                .projectKey(entity.getProjectKey())
                .blockers(entity.getBlockers())
                .criticals(entity.getCriticals())
                .majors(entity.getMajors())
                .minors(entity.getMinors())
                .infos(entity.getInfos()).build();
    }

    public static UserStatisticsEntry empty() {
        return UserStatisticsEntry.builder()
                .blockers(0L)
                .criticals(0L)
                .majors(0L)
                .minors(0L)
                .infos(0L).build();
    }
}
