package net.lipecki.sqcompanion.group;

import net.lipecki.sqcompanion.project.ProjectsService;
import net.lipecki.sqcompanion.sonarqube.issue.SonarQubeIssuesIssueResultDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultCellsDto;
import net.lipecki.sqcompanion.sonarqube.timemachine.SonarQubeTimeMachineResultsDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gregorry on 25.09.2015.
 */
@RestController
@RequestMapping("/api/group")
public class GroupRestController {

    public static final int HOUR_MS = 1000 * 60 * 60;
    public static final int DAY_MS = 1000 * 60 * 60 * 24;
    private final ProjectsService projectsService;

    public GroupRestController(final ProjectsService projectsService) {
        this.projectsService = projectsService;

        groups.put("eximee", new Group("eximee", "Eximee",
                "pl.consdata.eximee.webforms:webforms",
                "pl.consdata.eximee:eximee-alfresco-core",
                "pl.consdata.eximee:repository:mssql",
                "pl.consdata.eximee:repository:postgresql",
                "pl.consdata.eximee:eximee-parking",
                "pl.consdata.eximee:eximee-dms-connector",
                "pl.consdata.iew.serviceproxy:serviceproxy",
                "pl.consdata.eximee:router"));
        groups.put("eximee-bzwbk", new Group("eximee-bzwbk", "Eximee BZWBK",
                "pl.consdata.eximee.bzwbk:webforms-bzwbk",
                "pl.consdata.eximee.webforms:bzwbk-serviceproxy",
                "pl.consdata.eximee.bzwbk.alfresco:eximee-alfresco-bzwbk",
                "pl.consdata.reports:eximee-report-bzwbk",
                "pl.consdata.eximee.bzwbk:eximee-router-bzwbk"));
        groups.put("eximee-mbank", new Group("eximee-mbank", "Eximee mBank",
                "pl.consdata.reports:rpt-bre-dataprocessor-reactor",
                "pl.consdata.eximee.mbank.alfresco:eximee-mbank-alfresco",
                "pl.consdata.eximee:eximee-mbank-status"));
        groups.put("ib24", new Group("ib24", "iBiznes24",
                "pl.com.pbpolsoft.emb24:emb24-app:default"));

        groups.put("mbank-dpd", new Group("mbank-dpd", "mBank/DPD",
                "pl.consdata.iew.serviceproxy:eximee-mbank-serviceproxy-dpd",
                "pl.consdata.iew.serviceproxy:eximee-mbank-serviceproxy-disposition",
                "pl.consdata.iew.serviceproxy:eximee-mbank-serviceproxy-commons"));
    }

    @RequestMapping(value = "/infos", method = RequestMethod.GET)
    public List<GroupInfo> getGroups() {
        return groups
                .values()
                .stream()
                .map(g -> GroupInfo.of(g))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/summaries", method = RequestMethod.GET)
    public List<GroupSummary> getGroupSummaries() {
        return groups
                .values()
                .stream()
                .map(g -> fillWithSummaryData(GroupSummary.of(g), g))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GroupDetails getGroup(@PathVariable final String id) {
        if (groups.containsKey(id)) {
            return fillWithDetailsData(GroupDetails.of(groups.get(id)), groups.get(id));
        } else {
            return null;
        }
    }

    private GroupSummary fillWithSummaryData(final GroupSummary groupSummary, final Group group) {
        groupSummary
                .setBlockers(group
                        .getProjects()
                        .stream()
                        .mapToInt(projectsService::getBlockerCount)
                        .sum());
        groupSummary
                .setCriticals(group
                        .getProjects()
                        .stream()
                        .mapToInt(projectsService::getCriticalCount)
                        .sum());

        if (groupSummary.getBlockers() > 0) {
            groupSummary.setStatus(StatusCode.BLOCKER);
        } else if (groupSummary.getCriticals() > 0) {
            groupSummary.setStatus(StatusCode.CRITICAL);
        } else {
            groupSummary.setStatus(StatusCode.HEALTHY);
        }


        return groupSummary;
    }

    private GroupDetails fillWithDetailsData(final GroupDetails groupDetails, final Group group) {

        groupDetails.setProjects(group
                .getProjects()
                .stream()
                .map(p -> projectsService.getProjectSummary(p))
                .collect(Collectors.toList()));

        final List<SonarQubeIssuesIssueResultDto> blockers = group.getProjects()
                .stream()
                .map(p -> projectsService.getBlockers(p))
                .collect(ArrayList::new, List::addAll, List::addAll);

        final List<SonarQubeIssuesIssueResultDto> criticals = group.getProjects()
                .stream()
                .map(p -> projectsService.getCriticals(p))
                .collect(ArrayList::new, List::addAll, List::addAll);

        groupDetails.setBlockers(blockers.size());
        groupDetails.setCriticals(criticals.size());
        groupDetails
                .setOtherIssues(group
                        .getProjects()
                        .stream()
                        .mapToInt(projectsService::getOtherIssueCount)
                        .sum());

        if (groupDetails.getBlockers() > 0) {
            groupDetails.setStatus(StatusCode.BLOCKER);
        } else if (groupDetails.getCriticals() > 0) {
            groupDetails.setStatus(StatusCode.CRITICAL);
        } else {
            groupDetails.setStatus(StatusCode.HEALTHY);
        }

        final List<SonarQubeIssuesIssueResultDto> issues = new ArrayList<>();
        issues.addAll(blockers);
        issues.addAll(criticals);

        groupDetails.setIssues(issues
                .stream()
                .map(i -> Issue.of(i))
                .collect(Collectors.toList()));

        final Optional<Date> newsetIssueDate = issues
                .stream()
                .map(i -> i.getCreationDate())
                .sorted(Collections.reverseOrder())
                .findFirst();

        if (newsetIssueDate.isPresent()) {
            final int age = (int) Math.floor(
                    (new Date().getTime() - newsetIssueDate.get().getTime())
                            / (1000 * 60 * 60 * 24));
            groupDetails.setHealthyStreak(age);
        } else {
            groupDetails.setHealthyStreak(-1);
        }

        final List<SonarQubeTimeMachineResultsDto> rawHistData = group.getProjects()
                .stream()
                .map(p -> projectsService.getHistoricalData(p))
                .collect(Collectors.toList());

        final Map<Date, IssuesHistoryPoint> combinedHistory = new HashMap<>();
        for (final SonarQubeTimeMachineResultsDto rawData : rawHistData) {
            final SonarQubeTimeMachineResultCellsDto[] cells = rawData.getCells();
            for (final SonarQubeTimeMachineResultCellsDto cell : cells) {
                final Date date = normalizeDateToDay(cell.getD());
                if (!combinedHistory.containsKey(date)) {
                    combinedHistory.put(date, new IssuesHistoryPoint(date, 0, 0));
                }
                final IssuesHistoryPoint point = combinedHistory.get(date);
                point.setBlockers(point.getBlockers() + Integer.parseInt(cell.getV()[0]));
                point.setCriticals(point.getCriticals() + Integer.parseInt(cell.getV()[1]));
            }
        }
        groupDetails.setHistoricalData(combinedHistory.values()
                .stream()
                .sorted((o1, o2) -> -1 * o1.getDate().compareTo(o2.getDate()))
                .limit(60)
                .collect(Collectors.toList()));

        return groupDetails;
    }

    private Date normalizeDateToDay(final Date date) {
        long msPortion = date.getTime() % DAY_MS;
        return new Date(date.getTime() - msPortion + HOUR_MS);
    }

    private final Map<String, Group> groups = new HashMap<>();

}
