package net.lipecki.sqcompanion.health;

import net.lipecki.sqcompanion.repository.Project;

/**
 * @author gregorry
 */
@FunctionalInterface
public interface HealthVoter {

	HealthStatus checkHealth(Project project);
	
}
