package net.lipecki.sqcompanion.violations;

import io.swagger.annotations.ApiOperation;
import net.lipecki.sqcompanion.SQCompanionException;
import net.lipecki.sqcompanion.history.ProjectHistoryRepository;
import net.lipecki.sqcompanion.repository.Group;
import net.lipecki.sqcompanion.repository.RepositoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ViolationsHistory getRootGroupViolationsHistory() {
		return getGroupViolationsHistory(repositoryService.getRootGroup());
	}

	@RequestMapping(
			value = "/group/{uuid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	)
	@ApiOperation(
			value = "Returns group violations history"
	)
	public ViolationsHistory getGroupViolationsHistory(@PathVariable final String uuid) {
		final Optional<Group> group = repositoryService.getGroup(uuid);
		if (group.isPresent()) {
			return getGroupViolationsHistory(group.get());
		} else {
			throw new SQCompanionException("Can't find requested group uuid: " + uuid);
		}
	}

	private ViolationsHistory getGroupViolationsHistory(final Group group) {
		final List<ViolationHistoryEntry> history = group
				.getAllProjects()
				.stream()
				.flatMap(project -> projectHistoryRepository.findAllByProjectKey(project.getKey()).stream())
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

}
