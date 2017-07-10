package net.lipecki.sqcompanion.group;

import net.lipecki.sqcompanion.SQCompanionException;
import net.lipecki.sqcompanion.health.HealthCheckService;
import net.lipecki.sqcompanion.health.HealthStatus;
import net.lipecki.sqcompanion.project.ProjectSummary;
import net.lipecki.sqcompanion.project.ProjectSummaryService;
import net.lipecki.sqcompanion.project.ProjectViolations;
import net.lipecki.sqcompanion.repository.Group;
import net.lipecki.sqcompanion.repository.RepositoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

	private final RepositoryService repositoryService;
	private final ProjectSummaryService projectSummaryService;
	private final HealthCheckService healthCheckService;

	public GroupController(
			final RepositoryService repositoryService,
			final ProjectSummaryService projectSummaryService,
			final HealthCheckService healthCheckService) {
		this.repositoryService = repositoryService;
		this.projectSummaryService = projectSummaryService;
		this.healthCheckService = healthCheckService;
	}

	@RequestMapping({"", "/"})
	public GroupDetails getRootGroup() {
		return asGroupDetails(repositoryService.getRootGroup());
	}

	@RequestMapping("/{uuid}")
	public GroupDetails getGroup(@PathVariable final String uuid) {
		final Optional<Group> group = repositoryService.getGroup(uuid);
		if (group.isPresent()) {
			return asGroupDetails(group.get());
		} else {
			throw new SQCompanionException("Can't find requested group uuid: " + uuid);
		}
	}

	private GroupDetails asGroupDetails(final Group group) {
		final List<ProjectSummary> projectSummaries = projectSummaryService.getProjectSummaries(group.getAllProjects());
		final HealthStatus healthStatus = healthCheckService.getCombinedProjectsHealth(projectSummaries);

		return GroupDetails
				.builder()
				.groups(group.getGroups().stream().map(this::asGroupSummary).collect(Collectors.toList()))
				.uuid(group.getUuid())
				.name(group.getName())
				.projects(projectSummaries)
				.healthStatus(healthStatus)
				.blockers(getProjectViolationsSum(projectSummaries, ProjectViolations::getBlockers))
				.criticals(getProjectViolationsSum(projectSummaries, ProjectViolations::getCriticals))
				.majors(getProjectViolationsSum(projectSummaries, ProjectViolations::getMajors))
				.minors(getProjectViolationsSum(projectSummaries, ProjectViolations::getMinors))
				.infos(getProjectViolationsSum(projectSummaries, ProjectViolations::getInfos))
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
				.build();
	}

	private int getProjectViolationsSum(final List<ProjectSummary> projectSummaries, final ToIntFunction<ProjectViolations> violationsExtractor) {
		return projectSummaries.stream().map(ProjectSummary::getViolations).mapToInt(violationsExtractor).sum();
	}

}
