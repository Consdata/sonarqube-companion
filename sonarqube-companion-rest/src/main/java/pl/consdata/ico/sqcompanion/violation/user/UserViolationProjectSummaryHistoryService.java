package pl.consdata.ico.sqcompanion.violation.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.health.HealthCheckService;
import pl.consdata.ico.sqcompanion.members.MemberService;
import pl.consdata.ico.sqcompanion.project.ProjectSummary;
import pl.consdata.ico.sqcompanion.repository.Project;

import java.util.List;
import java.util.stream.Collectors;

import static pl.consdata.ico.sqcompanion.project.ProjectSummary.summarizedViolations;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserViolationProjectSummaryHistoryService {
    private final MemberService memberService;
    private final HealthCheckService healthCheckService;

    public ProjectSummary getUserViolationProjectSummaryHistory(Project project, String userId) {
        // get all aliases and merge
        return null;
    }

    public ProjectSummary getGroupMembersViolationProjectSummaryHistory(Project project, String groupId) {
        List<ProjectSummary> membersProjectSummaries = memberService.groupMembers(groupId).stream().map(member -> getUserViolationProjectSummaryHistory(project, member.getUuid())).collect(Collectors.toList());
        return ProjectSummary.builder()
                .name(project.getName())
                .key(project.getKey())
                .serverId(project.getServerId())
                .serverUrl(project.getServerUrl())
                .healthStatus(healthCheckService.checkHealth(project))
                .violations(summarizedViolations(membersProjectSummaries))
                .build();
    }
}
