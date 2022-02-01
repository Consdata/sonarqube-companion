package pl.consdata.ico.sqcompanion.violation.group.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationHistoryRepository;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveSummariesService {
    private final UserViolationHistoryRepository userViolationHistoryRepository;
    private final GroupViolationSummaryHistoryRepository groupViolationSummaryHistoryRepository;

    @Transactional
    public void delete(LocalDate date) {
        log.info("Deleting summaries from {}", date);
        groupViolationSummaryHistoryRepository.deleteByDate(date);
        log.info("Deleted");
    }
}
