package pl.consdata.ico.sqcompanion.violation.user.diff;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserViolationDiffRepository extends JpaRepository<UserProjectViolationDiffHistoryEntry, Long> {

    List<UserProjectViolationDiffHistoryEntry> findByUserId(String userId);

    Optional<UserProjectViolationDiffHistoryEntry> findFirstByUserIdAndProjectKeyOrderByDateDesc(String key, String userId);

}
