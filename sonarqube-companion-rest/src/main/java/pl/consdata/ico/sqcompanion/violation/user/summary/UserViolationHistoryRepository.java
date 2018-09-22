package pl.consdata.ico.sqcompanion.violation.user.summary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserViolationHistoryRepository extends JpaRepository<UserProjectSummaryViolationHistoryEntry, Long> {

    List<UserProjectSummaryViolationHistoryEntry> findByUserId(String user);

    Optional<UserProjectSummaryViolationHistoryEntry> findFirstByUserIdAndProjectKeyOrderByDateDesc(String user, String project);

}
