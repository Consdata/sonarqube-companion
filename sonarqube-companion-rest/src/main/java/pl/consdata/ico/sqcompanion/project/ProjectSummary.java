package pl.consdata.ico.sqcompanion.project;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.health.HealthStatus;
import pl.consdata.ico.sqcompanion.violation.Violations;

import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * @author gregorry
 */
@Data
@Builder
public class ProjectSummary {

    private String name;
    private String key;
    private String serverId;
    private String serverUrl;
    private HealthStatus healthStatus;
    private Violations violations;

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

}
