package net.lipecki.sqcompanion.health;

import net.lipecki.sqcompanion.repository.Project;

/**
 * @author gregorry
 */
public interface HealthVoter {

	HealthStatus checkHealth(Project project);
	
}
