package pl.consdata.ico.sqcompanion.group;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.project.ProjectSummaryService;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.violation.user.summary.UserViolationSummaryHistoryService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final ProjectSummaryService projectSummaryService;
    private final UserViolationSummaryHistoryService userViolationSummaryHistoryService;
    private final HealthCheckService healthCheckService;

    @Cacheable(value = Caches.GROUP_DETAILS_CACHE, sync = true, key = "#group.uuid")
    public GroupDetails getGroupDetails(Group group) {
        final List<ProjectSummary> projectSummaries = userViolationSummaryHistoryService.getProjectSummaries(group.getAllProjects(), group.getUuid());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);

        return GroupDetails
                .builder()
                .groups(ofNullable(group.getGroups()).orElse(emptyList()).stream().map(this::asGroupSummary).collect(Collectors.toList()))
                .uuid(group.getUuid())
                .name(group.getName())
                .projects(projectSummaries)
                .healthStatus(healthStatus)
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .events(group.getEvents())
                .build();
    }

    //@Cacheable(value = Caches.GROUP_DETAILS_CACHE, sync = true, key = "#group.uuid")
    public GroupDetails getRootGroupDetails(Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);

        return GroupDetails
                .builder()
                .groups(ofNullable(group.getGroups()).orElse(emptyList()).stream().map(this::asGroupSummary).collect(Collectors.toList()))
                .uuid(group.getUuid())
                .name(group.getName())
                .projects(projectSummaries)
                .healthStatus(healthStatus)
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .events(group.getEvents())
                .build();
    }



    private GroupSummary asGroupSummary(final Group group) {
        final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);
        return GroupSummary
                .builder()
                .healthStatus(healthStatus)
                .uuid(group.getUuid())
                .name(group.getName())
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .build();
    }

}
