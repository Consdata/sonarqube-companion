package pl.consdata.ico.sqcompanion.summary;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.repository.Group;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

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
