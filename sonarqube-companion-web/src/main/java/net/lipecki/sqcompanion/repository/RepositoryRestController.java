package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.group.*;
import net.lipecki.sqcompanion.project.ProjectSummaryDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
                .flatMap(group -> Optional.of(
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
                                        .getAll()
                                        .stream()
                                        .map(i -> mapAsIssueDto(i))
                                        .collect(toList()),
                                group
                                        .getIssues()
                                        .getSignificant()
                                        .stream()
                                        .sorted((a, b) -> byCreationDateReverse(a, b))
                                        .findFirst()
                                        .map(issue -> getWihtoutIssueStreak(issue))
                                        .orElse(-1),
                                group
                                        .getHistory()
                                        .getHistoryPoints()
                                        .values()
                                        .stream()
                                        .map(point -> mapAsIssueHistoryPointDto(point))
                                        .collect(toList()))))
                .orElse(null);
    }

    private ProjectSummaryDto mapAsProjectSummaryDto(final Project project) {
        return new ProjectSummaryDto(
                project.getKey(),
                project.getName(),
                project.getIssues().getBlockers().size(),
                project.getIssues().getCriticals().size(),
                StatusCodeDto.of(project.getHealthStatus()));
    }

    private IssuesHistoryPointDto mapAsIssueHistoryPointDto(final HistoryPoint point) {
        return new IssuesHistoryPointDto(
                new Date(point.getDate().toEpochDay()),
                point.getBlockers(),
                point.getCriticals());
    }

    private IssueDto mapAsIssueDto(final Issue i) {
        return new IssueDto(
                i.getKey(),
                i.getProject().map(p -> p.getName()).orElse("[missing]"),
                i.getComponent(),
                i.getMessage(),
                i.getSeverity().name(),
                i.getSeverity().getCode(),
                i.getCreationDate()
        );
    }

    private int byCreationDateReverse(final Issue a, final Issue b) {
        return -1 * a.getCreationDate().compareTo(b.getCreationDate());
    }

    private int getWihtoutIssueStreak(final Issue issue) {
        return (int) Math.floor((new Date().getTime() - issue.getCreationDate().getTime()) / DAY_MS);
    }

}
