package pl.consdata.ico.sqcompanion.overview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.members.MemberService;
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
public class OverviewService {

    private final MemberService memberService;
    private final UserViolationSummaryHistoryService userViolationSummaryHistoryService;
    private final HealthCheckService healthCheckService;

    @Cacheable(value = Caches.GROUP_OVERVIEW_CACHE, sync = true, key = "#group.uuid")
    public GroupOverview getOverview(Group group) {
        return asGroupWithSubGroupsSummary(group);
    }

    private GroupOverview asGroupWithSubGroupsSummary(final Group group) {
        final List<ProjectSummary> projectSummaries = userViolationSummaryHistoryService.getProjectSummaries(group.getAllProjects(), group.getUuid());
        final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);
        return GroupOverview
                .builder()
                .healthStatus(healthStatus)
                .uuid(group.getUuid())
                .name(group.getName())
                .violations(ProjectSummary.summarizedViolations(projectSummaries))
                .projectCount(projectSummaries.size())
                .membersCount(memberService.groupMembers(group.getUuid()).size())
                .groups(ofNullable(group.getGroups()).orElse(emptyList()).stream().map(this::asGroupWithSubGroupsSummary).collect(Collectors.toList()))
                .build();
    }

}
