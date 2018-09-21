package pl.consdata.ico.sqcompanion.violation.user.summary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.violation.ViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

@Slf4j
@Service
public class UserViolationSummaryHistoryService {

    private final UserViolationHistoryRepository repository;

    public UserViolationSummaryHistoryService(final UserViolationHistoryRepository repository) {
        this.repository = repository;
    }

    public ViolationsHistory userViolationsHistory(String user) {
        return ViolationsHistory.builder()
                .violationHistoryEntries(ViolationHistoryEntry.groupByDate(repository.findByUserId(user)))
                .build();
    }

}
