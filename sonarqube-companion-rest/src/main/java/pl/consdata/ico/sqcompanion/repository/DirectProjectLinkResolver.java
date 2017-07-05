package pl.consdata.ico.sqcompanion.repository;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.ProjectLink;

import java.util.Collections;
import java.util.List;

/**
 * @author gregorry
 */
@Service
public class DirectProjectLinkResolver implements ProjectLinkResolver {

	@Override
	public List<Project> resolveProjectLink(final ProjectLink projectLink) {
		return Collections.singletonList(
				Project.builder()
						.key(projectLink.getLink())
						.serverId(projectLink.getServerId())
						.build()
		);
	}

}
