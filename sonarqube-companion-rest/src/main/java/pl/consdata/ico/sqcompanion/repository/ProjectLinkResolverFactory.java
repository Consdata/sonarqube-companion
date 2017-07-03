package pl.consdata.ico.sqcompanion.repository;

import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.ProjectLinkType;

/**
 * @author gregorry
 */
@Service
public class ProjectLinkResolverFactory {

	private final DirectProjectLinkResolver directProjectLinkResolver;

	public ProjectLinkResolverFactory(final DirectProjectLinkResolver directProjectLinkResolver) {
		this.directProjectLinkResolver = directProjectLinkResolver;
	}

	public ProjectLinkResolver getResolver(final ProjectLinkType type) {
		if (ProjectLinkType.DIRECT == type) {
			return directProjectLinkResolver;
		} else {
			throw new RuntimeException(String.format("Unmapped project link type [type={}]", type));
		}
	}

}
