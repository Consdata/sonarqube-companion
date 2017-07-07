package net.lipecki.sqcompanion.repository;

import org.springframework.stereotype.Service;
import net.lipecki.sqcompanion.config.ProjectLinkType;

/**
 * @author gregorry
 */
@Service
public class ProjectLinkResolverFactory {

	private final DirectProjectLinkResolver directProjectLinkResolver;
	private final RegexProjectLinkResolver regexProjectLinkResolver;

	public ProjectLinkResolverFactory(
			final DirectProjectLinkResolver directProjectLinkResolver,
			final RegexProjectLinkResolver regexProjectLinkResolver) {
		this.directProjectLinkResolver = directProjectLinkResolver;
		this.regexProjectLinkResolver = regexProjectLinkResolver;
	}

	public ProjectLinkResolver getResolver(final ProjectLinkType type) {
		if (ProjectLinkType.DIRECT == type) {
			return directProjectLinkResolver;
		} else if (ProjectLinkType.REGEX == type) {
			return regexProjectLinkResolver;
		} else {
			throw new RuntimeException(String.format("Unmapped project link type [type=%s]", type));
		}
	}

}
