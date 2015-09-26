package net.lipecki.sqcompanion.group;

import net.lipecki.sqcompanion.project.ProjectsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gregorry on 25.09.2015.
 */
@Configuration
public class GroupInjector {

    @Bean
    public GroupRestController groupRestController(final ProjectsService projectsService) {
        return new GroupRestController(projectsService);
    }

}
