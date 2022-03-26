package pl.consdata.ico.sqcompanion.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.group.GroupService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.violation.group.summary.GroupViolationsHistoryService;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrepopulateCacheService {
    private final GroupViolationsHistoryService groupViolationsHistoryService;
    private final GroupService groupService;
    private final RepositoryService repositoryService;

    public void prepopulate() {
        LocalDate to = LocalDate.now().minusDays(1);
        LocalDate from = to.minusDays(30);
        prepopulateGroupViolationHistory(from, to);
    }

    private void prepopulateGroupViolationHistory(LocalDate from, LocalDate to) {
        repositoryService.getRootGroup().getAllGroups()
                .forEach(group -> {
                    prepopulateGroupViolationsHistory(group, from, to);
                    prepopulateGroupViolationsStatus(group, from, to);
                });
    }

    private void prepopulateGroupViolationsHistory(Group group, LocalDate from, LocalDate to) {
        log.info("Prepopulating violations history for {}[{}]", group.getName(), group.getUuid());
        groupViolationsHistoryService.getGroupViolationsHistory(group, from, to);
    }

    private void prepopulateGroupViolationsStatus(Group group, LocalDate from, LocalDate to) {
        log.info("Prepopulating violations status for {}[{}]", group.getName(), group.getUuid());
        groupService.getViolations(group, to);
        groupService.getViolationsDiff(group, from, to);
    }
}
