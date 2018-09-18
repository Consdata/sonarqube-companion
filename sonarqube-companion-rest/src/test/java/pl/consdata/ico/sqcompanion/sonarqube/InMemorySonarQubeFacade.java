package pl.consdata.ico.sqcompanion.sonarqube;

import pl.consdata.ico.sqcompanion.TestAppConfig;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilter;
import pl.consdata.ico.sqcompanion.util.LocalDateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gregorry
 */
public class InMemorySonarQubeFacade implements SonarQubeFacade {

    private InMemoryRepository inMemoryRepository;

    public InMemorySonarQubeFacade() {
        resetFacade();
    }

    public void resetFacade() {
        inMemoryRepository = InMemoryRepository
                .builder()
                .project(
                        Project.getProjectUniqueId(TestAppConfig.Servers.Server1.ID, TestAppConfig.RootGroup.Project1.KEY),
                        InMemoryProject
                                .builder()
                                .project(
                                        SonarQubeProject
                                                .builder()
                                                .key(TestAppConfig.RootGroup.Project1.KEY)
                                                .name(TestAppConfig.RootGroup.Project1.NAME)
                                                .build()
                                )
                                .issues(new ArrayList<>())
                                .measures(new ArrayList<>())
                                .build()
                )
                .user(TestAppConfig.Users.USER_1, SonarQubeUser.builder().userId(TestAppConfig.Users.USER_1).build())
                .build();
    }

    public InMemoryRepository getInMemoryRepository() {
        return inMemoryRepository;
    }

    @Override
    public List<SonarQubeProject> projects(final String serverId) {
        return inMemoryRepository
                .getProjects()
                .values()
                .stream()
                .map(InMemoryProject::getProject)
                .collect(Collectors.toList());
    }

    @Override
    public List<SonarQubeIssue> issues(final String serverId, final IssueFilter filter) {
        return inMemoryRepository.getProjects()
                .values()
                .stream()
                .filter(p -> filter.getComponentKeys() == null || filter.getComponentKeys().contains(p.getProject().getKey()))
                .flatMap(p -> p.getIssues().stream())
                .filter(i -> filter.getCreatedAfter() == null || !LocalDateUtil.asLocalDate(i.getCreationDate()).isBefore(filter.getCreatedAfter()))
                .filter(i -> filter.getCreatedBefore() == null || !LocalDateUtil.asLocalDate(i.getCreationDate()).isAfter(filter.getCreatedBefore()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SonarQubeUser> users(String serverId) {
        return new ArrayList<>(inMemoryRepository.getUsers().values());
    }

    @Override
    public List<SonarQubeMeasure> projectMeasureHistory(final String serverId, final String projectKey, final LocalDate fromLocalDate) {
        final Date fromDate = fromLocalDate != null ? Date.from(fromLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        return inMemoryRepository
                .getProjects()
                .get(Project.getProjectUniqueId(serverId, projectKey))
                .getMeasures()
                .stream()
                .filter(m -> fromLocalDate == null || m.getDate().after(fromDate))
                .collect(Collectors.toList());
    }

}
