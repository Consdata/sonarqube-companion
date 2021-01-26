package pl.consdata.ico.sqcompanion.violation.user.summary;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.consdata.ico.sqcompanion.violation.user.diff.UserProjectViolationDiffHistoryEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserViolationHistoryRepository extends JpaRepository<UserProjectSummaryViolationHistoryEntry, Long> {

    List<UserProjectSummaryViolationHistoryEntry> findByUserId(String user);

    boolean existsByProjectKeyAndUserIdIsIn(String project, List<String> users);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByUserIdAndProjectKeyOrderByDateDesc(String user, String project);

    List<UserProjectSummaryViolationHistoryEntry> findByProjectKeyAndUserIdIsInAndDateEquals(String key, List<String> users, LocalDate fromDate);

    List<UserProjectSummaryViolationHistoryEntry> findAllByProjectKeyAndUserIdIsIn(String key, List<String> users);

    List<UserProjectSummaryViolationHistoryEntry> findAllByProjectKeyAndUserIdIsInAndDateGreaterThanEqual(String key, List<String> users, LocalDate minusDays);

    UserProjectSummaryViolationHistoryEntry findFirstByProjectKeyAndUserIdOrderByDateDesc(String key, String user);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByProjectKeyAndUserIdIsInOrderByDateAsc(String key, List<String> users);
    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByProjectKeyAndUserIdIsInOrderByDateDesc(String key, List<String> users);
}
