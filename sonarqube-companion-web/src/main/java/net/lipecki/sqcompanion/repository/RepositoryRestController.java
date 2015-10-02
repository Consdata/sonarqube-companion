package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.dto.*;
import net.lipecki.sqcompanion.dto.ProjectSummaryDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by gregorry on 27.09.2015.
 */
@RestController
@RequestMapping("/api/repository")
public class RepositoryRestController {

    public static final int DAY_MS = 1000 * 60 * 60 * 24;
    private final RepositoryService repositoryService;

    public RepositoryRestController(final RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public RepositoryModel repositoryModel() {
        return repositoryService.getRepositoryModel();
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public List<GroupInfoDto> getGroups() {
        return repositoryService
                .getRepositoryModel()
                .getGroups()
                .stream()
                .map(g -> new GroupInfoDto(g.getKey(), g.getName()))
                .collect(toList());
    }

    @RequestMapping(value = "/groups/summaries", method = RequestMethod.GET)
    public List<GroupSummaryDto> getGroupSummaries() {
        return repositoryService
                .getRepositoryModel()
                .getGroups()
                .stream()
                .map(g -> new GroupSummaryDto(
                        g.getKey(),
                        g.getName(),
                        StatusCodeDto.of(g.getHealthStatus()),
                        g.getIssues().getBlockers().size(),
                        g.getIssues().getCriticals().size()))
                .collect(toList());
    }

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    public GroupDetailsDto getGroup(@PathVariable final String id) {
        return repositoryService
                .getRepositoryModel()
                .getGroup(id)
                .flatMap(group -> {
                    final Optional<Issue> lastSignificantIssue = group
                            .getIssues()
                            .getSignificant()
                            .stream()
                            .sorted((a, b) -> byCreationDateReverse(a, b))
                            .findFirst();
                    final Optional<Issue> lastAnyIssue = group
                            .getIssues()
                            .getAll()
                            .stream()
                            .sorted((a, b) -> byCreationDateReverse(a, b))
                            .findFirst();
                    return Optional.of(
                            new GroupDetailsDto(
                                    group.getKey(),
                                    group.getName(),
                                    group.getIssues().getBlockers().size(),
                                    group.getIssues().getCriticals().size(),
                                    group.getIssues().getNonSignificant().size(),
                                    StatusCodeDto.of(group.getHealthStatus()),
                                    group
                                            .getProjects()
                                            .stream()
                                            .map(project -> mapAsProjectSummaryDto(project))
                                            .collect(toList()),
                                    group
                                            .getIssues()
                                            .getSignificant()
                                            .stream()
                                            .map(issue -> mapAsIssueDto(issue))
                                            .collect(toList()),
                                    lastSignificantIssue
                                            .map(issue -> getWihtoutIssueStreak(issue))
                                            .orElse(-1),
                                    lastAnyIssue
                                            .map(issue -> getWihtoutIssueStreak(issue))
                                            .orElse(-1),
                                    group
                                            .getHistory()
                                            .getHistoryPoints()
                                            .values()
                                            .stream()
                                            .sorted((a, b) -> -1 * a.getDate().compareTo(b.getDate()))
                                            .limit(90)
                                            .map(historyPoint -> mapAsIssueHistoryPointDto(historyPoint))
                                            .collect(toList()),
                                    lastSignificantIssue
                                            .orElse(null),
                                    lastAnyIssue
                                            .orElse(null)));
                })
                .orElse(null);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Project getProject(@PathVariable final String id) {
        return repositoryService
                .getRepositoryModel()
                .getProject(id)
                .orElse(null);
    }

    private ProjectSummaryDto mapAsProjectSummaryDto(final Project project) {
        return new ProjectSummaryDto(
                project.getKey(),
                project.getName(),
                project.getSonarqubeKey(),
                project.getIssues().getBlockers().size(),
                project.getIssues().getCriticals().size(),
                StatusCodeDto.of(project.getHealthStatus()));
    }

    private IssuesHistoryPointDto mapAsIssueHistoryPointDto(final HistoryPoint point) {
        return new IssuesHistoryPointDto(
                Date.from(point.getDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                point.getBlockers(),
                point.getCriticals(),
                point.getMajors(),
                point.getMinors(),
                point.getInfos(),
                point.getAll(),
                point.getSignificant(),
                point.getNonsignificant());
    }

    private IssueDto mapAsIssueDto(final Issue issue) {
        return new IssueDto(
                issue.getKey(),
                issue.getProject().map(p -> p.getName()).orElse("[missing]"),
                issue.getComponent(),
                issue.getMessage(),
                issue.getSeverity().name(),
                issue.getSeverity().getCode(),
                issue.getCreationDate()
        );
    }

    private int byCreationDateReverse(final Issue a, final Issue b) {
        return -1 * a.getCreationDate().compareTo(b.getCreationDate());
    }

    private int getWihtoutIssueStreak(final Issue issue) {
        return (int) Math.floor((new Date().getTime() - issue.getCreationDate().getTime()) / DAY_MS);
    }

}
