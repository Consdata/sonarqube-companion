package pl.consdata.ico.sqcompanion.project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {

    @Bean
    public ProjectRepository projectRepository() {
        return new ProjectRepository();
    }

}
