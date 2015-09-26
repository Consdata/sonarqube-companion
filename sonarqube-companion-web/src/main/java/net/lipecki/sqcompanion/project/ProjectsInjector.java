package net.lipecki.sqcompanion.project;

import net.lipecki.sqcompanion.sonarqube.SonarQubeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gregorry on 26.09.2015.
 */
@Configuration
public class ProjectsInjector {

    @Bean
    public ProjectsService projectService(final SonarQubeService collector) {
        return new ProjectsService(collector);
    }

}
