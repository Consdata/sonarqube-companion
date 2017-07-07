package net.lipecki.sqcompanion.summary;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.lipecki.sqcompanion.repository.Group;
import net.lipecki.sqcompanion.repository.RepositoryService;

/**
 * @author gregorry
 */
@RestController
@RequestMapping("/groups")
public class GroupSummaryController {

	private final RepositoryService repositoryService;

	public GroupSummaryController(final RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@RequestMapping("")
	public Group getGroups() {
		return repositoryService.getRootGroup();
	}


}
