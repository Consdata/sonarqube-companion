package net.lipecki.sqcompanion.repository;

import net.lipecki.sqcompanion.sonarqube.SonarQubeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by gregorry on 26.09.2015.
 */
@Configuration
public class RepositoryInjector {

    @Bean
    public RepositoryRestController repositoryRestController(final RepositoryService repositoryService) {
        return new RepositoryRestController(repositoryService);
    }

    @Bean
    public RepositoryService modelRepository(final LayoutProvider layoutProvider, final SonarQubeService sonarQubeService) {
        final RepositoryService repositoryService = new RepositoryService(layoutProvider, sonarQubeService);
        repositoryService.loadData();
        return repositoryService;
    }

    @Bean
    public LayoutProvider layoutProvider(final ResourceLoader resourceLoader) {
        return new LayoutProvider(resourceLoader, 5, TimeUnit.MINUTES, layoutConfigurationFile);
    }

    @Bean
    public ResourceLoader resourceLoader() {
        return new ResourceLoader();
    }

    @Value("${app.layoutConfiguration}")
    private String layoutConfigurationFile;

    // TODO reload model periodically

}
