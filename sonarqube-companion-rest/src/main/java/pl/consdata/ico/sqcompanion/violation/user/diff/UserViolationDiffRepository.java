package pl.consdata.ico.sqcompanion.violation.user.diff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserViolationDiffRepository extends JpaRepository<UserProjectViolationDiffHistoryEntry, Long> {

    List<UserProjectViolationDiffHistoryEntry> findByUserId(String userId);

    List<UserProjectViolationDiffHistoryEntry> findByUserIdInAndProjectKeyAndDate(List<String> users, String projectKey, LocalDate date);

    Optional<UserProjectViolationDiffHistoryEntry> findFirstByUserIdAndProjectKeyOrderByDateDesc(String key, String userId);

}
