package pl.consdata.ico.sqcompanion.hook.action;

import lombok.extern.slf4j.Slf4j;
import pl.consdata.ico.sqcompanion.history.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.history.ViolationsHistoryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
public class NoImprovementWebhookAction implements WebhookAction<NoImprovementWebhookActionData> {

    private final RepositoryService repositoryService;
    private final ViolationsHistoryService violationsHistoryService;

    public NoImprovementWebhookAction(RepositoryService repositoryService, ViolationsHistoryService violationsHistoryService) {
        this.repositoryService = repositoryService;
        this.violationsHistoryService = violationsHistoryService;
    }

    @Override
    public ActionResponse call(String groupUUID, NoImprovementWebhookActionData actionData) {
        log.info("call");
        Group group = repositoryService.getGroup(groupUUID).orElse(null);
        if (group != null) {
            GroupViolationsHistoryDiff violationsHistoryDiff = violationsHistoryService.getGroupViolationsHistoryDiff(group, LocalDate.now().minusDays(1), LocalDate.now());
            if (!groupWasImproved(violationsHistoryDiff, actionData)) {
                return createResponse(group);
            }
        }
        return null;
    }

    private ActionResponse createResponse(Group group) {
        return new ActionResponse(new HashMap<>(), new HashMap<>());
    }

    private boolean groupWasImproved(GroupViolationsHistoryDiff violationsHistoryDiff, NoImprovementWebhookActionData actionData) {
        int diff = 0;
        if (actionData.getSeverity().contains("blockers")) {
            diff += violationsHistoryDiff.getRemovedViolations().getBlockers() - violationsHistoryDiff.getAddedViolations().getBlockers();
        }
        if (actionData.getSeverity().contains("criticals")) {
            diff += violationsHistoryDiff.getRemovedViolations().getCriticals() - violationsHistoryDiff.getAddedViolations().getCriticals();
        }
        if (actionData.getSeverity().contains("majors")) {
            diff += violationsHistoryDiff.getRemovedViolations().getMajors() - violationsHistoryDiff.getAddedViolations().getMajors();
        }
        if (actionData.getSeverity().contains("minors")) {
            diff += violationsHistoryDiff.getRemovedViolations().getMinors() - violationsHistoryDiff.getAddedViolations().getMinors();
        }
        if (actionData.getSeverity().contains("infos")) {
            diff += violationsHistoryDiff.getRemovedViolations().getInfos() - violationsHistoryDiff.getAddedViolations().getInfos();
        }
        log.info("diff: " + diff);
        return diff > 0;
    }

}
