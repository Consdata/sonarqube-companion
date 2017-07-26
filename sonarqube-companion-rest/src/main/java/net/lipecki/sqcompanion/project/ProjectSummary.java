package net.lipecki.sqcompanion.project;

import lombok.Builder;
import lombok.Data;
import net.lipecki.sqcompanion.health.HealthStatus;
import net.lipecki.sqcompanion.violations.Violations;

import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * @author gregorry
 */
@Data
@Builder
public class ProjectSummary {

	public static Violations summarizedViolations(final List<ProjectSummary> projectSummaries) {
		return Violations
				.builder()
				.blockers(sumProjectViolations(projectSummaries, Violations::getBlockers))
				.criticals(sumProjectViolations(projectSummaries, Violations::getCriticals))
				.majors(sumProjectViolations(projectSummaries, Violations::getMajors))
				.minors(sumProjectViolations(projectSummaries, Violations::getMinors))
				.infos(sumProjectViolations(projectSummaries, Violations::getInfos))
				.build();
	}

	public static int sumProjectViolations(final List<ProjectSummary> projectSummaries, final ToIntFunction<Violations> violationsExtractor) {
		return projectSummaries.stream().map(ProjectSummary::getViolations).filter(Objects::nonNull).mapToInt(violationsExtractor).sum();
	}

	private String name;
	private String key;
	private String serverId;
	private HealthStatus healthStatus;
	private Violations violations;

}
