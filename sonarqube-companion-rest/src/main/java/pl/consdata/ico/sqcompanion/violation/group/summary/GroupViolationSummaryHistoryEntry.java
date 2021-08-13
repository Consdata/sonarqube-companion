package pl.consdata.ico.sqcompanion.violation.group.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.Violations;
import pl.consdata.ico.sqcompanion.violation.user.ViolationHistorySource;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(name = "group_violations_summary_history_entries")
@Table(
        indexes = {
                @Index(name = "IDX_GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES_GROUP_ID", columnList = "groupId"),
                @Index(name = "IDX_GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES_GROUP_ID_PROJECT_KEY", columnList = "groupId,projectKey"),
                @Index(name = "IDX_GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES_GROUP_ID_PROJECT_KEY_USER_ID", columnList = "groupId,projectKey,userId"),
                @Index(name = "IDX_GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES_GROUP_ID_PROJECT_KEY_USER_ID_DATE", columnList = "groupId,projectKey,userId,date")
        }
)
@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class GroupViolationSummaryHistoryEntry implements ViolationHistorySource {

    @Id
    private String id;
    private String serverId;
    private String userId;
    private String groupId;
    private String projectKey;
    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;
    private LocalDate date;

    public static String combineId(final String serverId, final String groupId, final String projectKey, final String userId, final LocalDate date) {
        return String.format(
                "%s$%s$%s$%s$%s",
                serverId,
                groupId,
                projectKey,
                userId,
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }

    public static GroupViolationSummaryHistoryEntry empty() {
        return GroupViolationSummaryHistoryEntry
                .builder()
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0)
                .build();
    }

    public static Violations asViolations(List<GroupViolationSummaryHistoryEntry> entries) {
        Violations violations = Violations.empty();
        entries.forEach(violations::addViolations);
        return violations;
    }
}
