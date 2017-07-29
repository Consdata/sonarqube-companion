package pl.consdata.ico.sqcompanion.violations;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryEntry;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/violations/history")
public class ViolationsHistoryController {

	private final ProjectHistoryRepository projectHistoryRepository;
	private final RepositoryService repositoryService;

	public ViolationsHistoryController(
			final ProjectHistoryRepository projectHistoryRepository,
			final RepositoryService repositoryService) {
		this.projectHistoryRepository = projectHistoryRepository;
		this.repositoryService = repositoryService;
	}

	@RequestMapping(
			value = "/group",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	@ApiOperation(
			value = "Returns group violations history"
	)
	public ViolationsHistory getRootGroupViolationsHistory(@RequestParam Optional<Integer> daysLimit) {
		return getGroupViolationsHistory(repositoryService.getRootGroup(), daysLimit);
	}

	@RequestMapping(
			value = "/group/{uuid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	@ApiOperation(
			value = "Returns group violations history"
	)
	public ViolationsHistory getGroupViolationsHistory(@PathVariable final String uuid, @RequestParam Optional<Integer> daysLimit) {
		final Optional<Group> group = repositoryService.getGroup(uuid);
		if (group.isPresent()) {
			return getGroupViolationsHistory(group.get(), daysLimit);
		} else {
			throw new SQCompanionException("Can't find requested group uuid: " + uuid);
		}
	}

	private ViolationsHistory getGroupViolationsHistory(final Group group, Optional<Integer> daysLimit) {
		final List<ViolationHistoryEntry> history = group
				.getAllProjects()
				.stream()
				.flatMap(project -> getProjectViolationsHistory(project, daysLimit).stream())
				.map(
						entry -> ViolationHistoryEntry
								.builder()
								.date(entry.getDate())
								.violations(
										Violations
												.builder()
												.blockers(entry.getBlockers())
												.criticals(entry.getCriticals())
												.majors(entry.getMajors())
												.minors(entry.getMinors())
												.infos(entry.getInfos())
												.build()
								)
								.build()
				)
				.collect(
						Collectors.groupingBy(
								ViolationHistoryEntry::getDate,
								Collectors.reducing(ViolationHistoryEntry::sumEntries)
						)
				)
				.values()
				.stream()
				.filter(entry -> entry.isPresent())
				.map(entry -> entry.get())
				.collect(Collectors.toList())
				.stream()
				.sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
				.collect(Collectors.toList());
		return ViolationsHistory
				.builder()
				.violationHistoryEntries(history)
				.build();
	}

	private List<ProjectHistoryEntry> getProjectViolationsHistory(final Project project, final Optional<Integer> daysLimit) {
		if (daysLimit.isPresent()) {
			return projectHistoryRepository.findAllByProjectKeyAndDateGreaterThanEqual(
					project.getKey(),
					LocalDate.now().minusDays(daysLimit.get())
			);
		} else {
			return projectHistoryRepository.findAllByProjectKey(project.getKey());
		}
	}

}
