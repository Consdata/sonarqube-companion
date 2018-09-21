package pl.consdata.ico.sqcompanion.violation.user.summary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserViolationHistoryRepository extends JpaRepository<UserProjectSummaryViolationHistoryEntry, Long> {

    List<UserProjectSummaryViolationHistoryEntry> findByUserId(String user);

}
