package pl.consdata.ico.sqcompanion.violation.group.summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserProjectSummaryViolationHistoryEntry;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationHistoryRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SummaryResyncService {
    private final UserViolationHistoryRepository userViolationHistoryRepository;
    private final GroupViolationSummaryHistoryRepository groupViolationSummaryHistoryRepository;
    private final GroupViolationsHistoryService groupViolationsHistoryService;

    @Transactional
    public void resync(LocalDate date) {
        log.info("Resync summaries for {}", date);
        List<UserProjectSummaryViolationHistoryEntry> history = userViolationHistoryRepository.findAllByDate(date);
        if (!CollectionUtils.isEmpty(history)) {
            log.info("Deleteing summaries from {}", date);
            groupViolationSummaryHistoryRepository.deleteByDate(date);
            log.info("Deleted");
            log.info("Recreating summaries");
            history.forEach(item -> groupViolationsHistoryService.addToGroupHistory(item));
            log.info("Recreated");
        }
    }
}
