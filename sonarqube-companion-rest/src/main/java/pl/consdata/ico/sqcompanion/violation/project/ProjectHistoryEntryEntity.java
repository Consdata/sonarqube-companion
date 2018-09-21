package pl.consdata.ico.sqcompanion.violation.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.user.ViolationHistorySource;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(name = "project_history_entries")
@Table(
        indexes = {
                @Index(name = "IDX_HISTORY_ENTRIES_PROJECT_KEY", columnList = "projectKey"),
                @Index(name = "IDX_HISTORY_ENTRIES_PROJECT_KEY_DATE", columnList = "projectKey,date")
        }
)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectHistoryEntryEntity implements ViolationHistorySource {

    public static String combineId(final String serverId, final String projectKey, final LocalDate date) {
        return String.format(
                "%s$%s$%s",
                serverId,
                projectKey,
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    public static ProjectHistoryEntryEntity empty() {
        return ProjectHistoryEntryEntity
                .builder()
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0)
                .build();
    }

    @Id
    private String id;
    private String serverId;
    private String projectKey;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;
    private LocalDate date;

}
