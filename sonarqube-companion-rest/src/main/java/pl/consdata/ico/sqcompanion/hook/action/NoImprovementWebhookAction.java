package pl.consdata.ico.sqcompanion.hook.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.violation.project.GroupViolationsHistoryDiff;
import pl.consdata.ico.sqcompanion.violation.project.ProjectViolationsHistoryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import java.time.LocalDate;
import java.util.Collections;

@Slf4j
@Service
public class NoImprovementWebhookAction implements WebhookAction<NoImprovementWebhookActionData> {

    public static final String TYPE = "NO_IMPROVEMENT";
    private final RepositoryService repositoryService;
    private final ProjectViolationsHistoryService projectViolationsHistoryService;

    public NoImprovementWebhookAction(RepositoryService repositoryService, ProjectViolationsHistoryService projectViolationsHistoryService) {
        this.repositoryService = repositoryService;
        this.projectViolationsHistoryService = projectViolationsHistoryService;
    }

    @Override
    public ActionResponse call(String groupUUID, NoImprovementWebhookActionData actionData) {
        Group group = repositoryService.getGroup(groupUUID).orElse(null);
        if (group != null) {
            return checkImprovement(group, actionData);
        }
        return null;
    }

    private boolean isGroupClean(Group group, NoImprovementWebhookActionData actionData) {
        GroupViolationsHistoryDiff violationsHistoryDiff = projectViolationsHistoryService.getGroupViolationsHistoryDiff(group, LocalDate.ofEpochDay(0), LocalDate.now());
        return countDiff(violationsHistoryDiff, actionData) == 0;
    }

    private ActionResponse checkImprovement(Group group, NoImprovementWebhookActionData actionData) {
        if (isGroupClean(group, actionData)) {
            return createResponse("clean", null);
        }

        GroupViolationsHistoryDiff violationsHistoryDiff = projectViolationsHistoryService.getGroupViolationsHistoryDiff(group, LocalDate.now().minusDays(1), LocalDate.now());
        if (groupWasImproved(violationsHistoryDiff, actionData)) {
            return createResponse("improvement", countDiff(violationsHistoryDiff, actionData));
        } else {
            return createResponse("no_improvement", countDiff(violationsHistoryDiff, actionData));
        }
    }

    private ActionResponse createResponse(String result, Integer diff) {
        if (diff == null) {
            return new ActionResponse(Collections.emptyMap(), result);
        } else {
            return new ActionResponse(Collections.singletonMap("diff", String.valueOf(diff)), result);
        }
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
        return diff > 0;
    }

}
