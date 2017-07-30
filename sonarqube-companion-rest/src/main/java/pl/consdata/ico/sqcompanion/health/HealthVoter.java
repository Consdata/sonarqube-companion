package pl.consdata.ico.sqcompanion.health;

import pl.consdata.ico.sqcompanion.repository.Project;

/**
 * @author gregorry
 */
@FunctionalInterface
public interface HealthVoter {

    HealthStatus checkHealth(Project project);

}
