package net.lipecki.sqcompanion.repository;

import lombok.extern.slf4j.Slf4j;
import net.lipecki.sqcompanion.config.AppConfig;
import net.lipecki.sqcompanion.config.GroupDefinition;
import net.lipecki.sqcompanion.config.ProjectLink;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Możliwości rozwoju:
 * - odświeżanie automatyczne przez cron
 *
 * @author gregorry
 */
@Slf4j
@Service
public class RepositoryService {

	private final AppConfig appConfig;
	private final ProjectLinkResolverFactory projectLinkResolverFactory;
	private Group rootGroup;

	public RepositoryService(final AppConfig appConfig, final ProjectLinkResolverFactory projectLinkResolverFactory) {
		this.appConfig = appConfig;
		this.projectLinkResolverFactory = projectLinkResolverFactory;
		syncGroups();
	}

	public Group getRootGroup() {
		return rootGroup;
	}

	public void syncGroups() {
		final GroupDefinition rootGroupConfig = appConfig.getRootGroup();
		if (Objects.nonNull(rootGroupConfig)) {
			this.rootGroup = buildGroup(rootGroupConfig);
		} else {
			log.info("Root group not synced due to empty configuration");
		}
	}

	private Group buildGroup(final GroupDefinition group) {
		final List<Group> subGroups = group.getGroups().stream().map(this::buildGroup).collect(Collectors.toList());
		final List<Project> projects = group.getProjectLinks()
				.stream()
				.flatMap(this::linkProjects)
				.collect(Collectors.toList());
		return Group.builder()
				.uuid(group.getUuid())
				.name(group.getName())
				.groups(subGroups)
				.projects(projects)
				.build();
	}

	private Stream<Project> linkProjects(final ProjectLink projectLink) {
		return projectLinkResolverFactory
				.getResolver(projectLink.getType())
				.resolveProjectLink(projectLink)
				.stream();
	}

}
