package pl.consdata.ico.sqcompanion.violation.group.summary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GroupViolationSummaryHistoryRepository extends JpaRepository<GroupViolationSummaryHistoryEntry, Long> {

    Optional<GroupViolationSummaryHistoryEntry> findFirstByGroupIdAndProjectKeyAndDate(String groupId, String projectKey, LocalDate date);

    Optional<GroupViolationSummaryHistoryEntry> findFirstByGroupIdAndProjectKeyAndUserIdAndDate(String groupId, String projectKey, String userId, LocalDate date);

    List<GroupViolationSummaryHistoryEntry> findAllByGroupIdAndProjectKeyInAndDateBetween(String groupId, List<String> projectKey, LocalDate from, LocalDate to);

    List<GroupViolationSummaryHistoryEntry> findAllByGroupIdAndProjectKeyAndDateBetween(String groupId, String projectKey, LocalDate from, LocalDate to);

    List<GroupViolationSummaryHistoryEntry> findAllByGroupIdAndDateBetween(String groupId, LocalDate from, LocalDate to);

    List<GroupViolationSummaryHistoryEntry> findAllByGroupIdAndDateBetweenAndUserIdIn(String groupId, LocalDate from, LocalDate to, List<String> userIds);

    @Query(value = "select PROJECT_KEY as projectKey, DATE as date, SUM(BLOCKERS) as blockers, SUM(CRITICALS) as criticals, SUM(MAJORS) as majors, SUM(MINORS) as minors, SUM(INFOS) as infos from GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES where GROUP_ID = :groupId and date = :date group by PROJECT_KEY, DATE", nativeQuery = true)
    List<GroupProjectSummaryProjection> groupByProjects(@Param("groupId") String groupId, @Param("date") LocalDate date);

    @Query(value = "select USER_ID as userId, DATE as date, SUM(BLOCKERS) as blockers, SUM(CRITICALS) as criticals, SUM(MAJORS) as majors, SUM(MINORS) as minors, SUM(INFOS) as infos from GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES where GROUP_ID = :groupId and date = :date group by USER_ID, DATE", nativeQuery = true)
    List<GroupMemberSummaryProjection> groupByMembers(@Param("groupId") String groupId, @Param("date") LocalDate date);

    @Query(value = "select SUM(BLOCKERS) as blockers, SUM(CRITICALS) as criticals, SUM(MAJORS) as majors, SUM(MINORS) as minors, SUM(INFOS) as infos from GROUP_VIOLATIONS_SUMMARY_HISTORY_ENTRIES where GROUP_ID = :groupId and DATE = :date group by GROUP_ID ", nativeQuery = true)
    Optional<GroupViolationsSummaryProjection> groupViolations(@Param("groupId") String groupId, @Param("date")  LocalDate date);
}
