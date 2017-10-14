package pl.consdata.ico.sqcompanion.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeIssue;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeMeasure;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeProject;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@Profile("demo")
public class DemoSonarQubeFacade implements SonarQubeFacade {

    private final DemoDefinition demoDefinition;

    public DemoSonarQubeFacade(final DemoDefinition demoDefinition) {
        this.demoDefinition = demoDefinition;
    }

    @Override
    public List<SonarQubeProject> getProjects(String serverId) {
        final DemoServer server = demoDefinition.getServers().get(serverId);
        if (server != null) {
            return server
                    .getProjects()
                    .stream()
                    .map(
                            demoProject -> SonarQubeProject
                                    .builder()
                                    .key(demoProject.getKey())
                                    .name(demoProject.getName())
                                    .build()
                    )
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<SonarQubeIssue> getIssues(String serverId, String projectKey) {
        return new ArrayList<>();
    }

    @Override
    public List<SonarQubeMeasure> getProjectMeasureHistory(String serverId, String projectKey, LocalDate fromDate) {
        final DemoServer server = demoDefinition.getServer(serverId).orElseThrow(() -> new SQCompanionException("Can't find matching serverId"));
        final DemoProject project = server.getProject(projectKey).orElseThrow(() -> new SQCompanionException("Can't find matching projectKey"));
        return project.getHistory()
                .stream()
                .map(this::asSonarQubeMeasure)
                .collect(Collectors.toList());
    }

    private SonarQubeMeasure asSonarQubeMeasure(DemoHistoricMeasure demoHistoricMeasure) {
        return SonarQubeMeasure
                .builder()
                .blockers(demoHistoricMeasure.getBlockers())
                .criticals(demoHistoricMeasure.getCriticals())
                .majors(demoHistoricMeasure.getMajors())
                .minors(demoHistoricMeasure.getMinors())
                .infos(demoHistoricMeasure.getInfos())
                .date(LocalDateUtil.asDate(getDateByRelativeDate(demoHistoricMeasure.getRelativeDate())))
                .build();
    }

    private LocalDate getDateByRelativeDate(final String relativeDate) {
        return RelativeDate.of(relativeDate).from(LocalDate.now());
    }

}
