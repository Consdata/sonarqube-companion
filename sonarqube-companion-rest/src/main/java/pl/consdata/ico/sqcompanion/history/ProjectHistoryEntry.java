package pl.consdata.ico.sqcompanion.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;

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
public class ProjectHistoryEntry {

    @Id
    private String id;
    private String projectKey;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;
    private LocalDate date;

}
