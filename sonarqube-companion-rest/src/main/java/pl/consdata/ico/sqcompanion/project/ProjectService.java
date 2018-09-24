package pl.consdata.ico.sqcompanion.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.ServerDefinition;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;

    public ProjectService(final ProjectRepository projectRepository, final SonarQubeFacade sonarQubeFacade, final AppConfig appConfig) {
        this.projectRepository = projectRepository;
        this.sonarQubeFacade = sonarQubeFacade;
        this.appConfig = appConfig;
    }

    @Transactional
    public void syncProjects() {
        log.info("Syncing available projects from all SonarQube servers");
        appConfig.getServers()
                .stream()
                .map(ServerDefinition::getId)
                .forEach(serverId -> syncServerProjects(serverId));
    }

    public List<ProjectEntity> getProjects(String serverId) {
        return projectRepository.findAllByServerId(serverId);
    }

    private void syncServerProjects(final String serverId) {
        log.info("Syncing projects from {}", serverId);
        final List<ProjectEntity> projects = sonarQubeFacade.projects(serverId)
                .stream()
                .map(
                        project -> ProjectEntity
                                .builder()
                                .key(project.getKey())
                                .name(project.getName())
                                .serverId(serverId)
                                .build()
                )
                .collect(Collectors.toList());
        projectRepository.deleteAllByServerId(serverId);
        projectRepository.saveAll(projects);
    }

}
