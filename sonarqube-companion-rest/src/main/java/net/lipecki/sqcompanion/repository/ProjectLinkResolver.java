package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.config.ProjectLink;

import java.util.List;

/**
 * @author gregorry
 */
public interface ProjectLinkResolver {

	List<Project> resolveProjectLink(final ProjectLink projectLink);

}
