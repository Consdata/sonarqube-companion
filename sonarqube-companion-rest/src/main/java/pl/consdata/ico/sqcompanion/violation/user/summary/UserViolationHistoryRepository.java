package pl.consdata.ico.sqcompanion.violation.user.summary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserViolationHistoryRepository extends JpaRepository<UserProjectSummaryViolationHistoryEntry, Long> {

    List<UserProjectSummaryViolationHistoryEntry> findByUserId(String user);

    boolean existsByProjectKeyAndUserIdIsIn(String project, Set<String> users);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByUserIdAndProjectKeyOrderByDateDesc(String user, String project);

    List<UserProjectSummaryViolationHistoryEntry> findByProjectKeyAndUserIdIsInAndDateEquals(String key, Set<String> users, LocalDate fromDate);

    List<UserProjectSummaryViolationHistoryEntry> findAllByProjectKeyAndUserIdIsIn(String key, Set<String> users);

    List<UserProjectSummaryViolationHistoryEntry> findAllByProjectKeyAndUserIdIsInAndDateGreaterThanEqual(String key, Set<String> users, LocalDate minusDays);

    UserProjectSummaryViolationHistoryEntry findFirstByProjectKeyAndUserIdOrderByDateDesc(String key, String user);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByProjectKeyAndUserIdIsInOrderByDateAsc(String key, Set<String> users);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByProjectKeyAndUserIdIsInOrderByDateDesc(String key, Set<String> users);

    List<UserProjectSummaryViolationHistoryEntry> findAllByDate(LocalDate date);
}
