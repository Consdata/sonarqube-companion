package pl.consdata.ico.sqcompanion.hook.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.history.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.history.ViolationsHistoryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
@Service
public class NoImprovementWebhookAction implements WebhookAction<NoImprovementWebhookActionData> {

    public static final String TYPE = "NO_IMPROVEMENT";
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
            return checkImprovement(group, actionData);
        }
        return null;
    }

    private boolean isGroupClean(Group group, NoImprovementWebhookActionData actionData) {
        GroupViolationsHistoryDiff violationsHistoryDiff = violationsHistoryService.getGroupViolationsHistoryDiff(group, LocalDate.ofEpochDay(0), LocalDate.now());
        return countDiff(violationsHistoryDiff, actionData) == 0;
    }

    private ActionResponse checkImprovement(Group group, NoImprovementWebhookActionData actionData) {
        if (isGroupClean(group, actionData)) {
            return createResponse("clean");
        }

        GroupViolationsHistoryDiff violationsHistoryDiff = violationsHistoryService.getGroupViolationsHistoryDiff(group, LocalDate.now().minusDays(1), LocalDate.now());
        if (groupWasImproved(violationsHistoryDiff, actionData)) {
            return createResponse("improvement");
        } else {
            return createResponse("no_improvement");
        }
    }

    private ActionResponse createResponse(String result) {
        return new ActionResponse(new HashMap<>(), new HashMap<>(), result);
    }

    private int countDiff(GroupViolationsHistoryDiff violationsHistoryDiff, NoImprovementWebhookActionData actionData) {
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
        return diff;
    }

    private boolean groupWasImproved(GroupViolationsHistoryDiff violationsHistoryDiff, NoImprovementWebhookActionData actionData) {
        int diff = countDiff(violationsHistoryDiff, actionData);
        log.info("diff: " + diff);
        return diff > 0;
    }

}
