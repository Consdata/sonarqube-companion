package pl.consdata.ico.sqcompanion.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.history.ProjectHistoryRepository;
import pl.consdata.ico.sqcompanion.project.ProjectRepository;
import pl.consdata.ico.sqcompanion.sync.SynchronizationStateRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Profile("demo")
@Slf4j
public class DemoInitializer {

    @Autowired
    private DemoSonarQubeFacade demoSonarQubeFacade;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectHistoryRepository projectHistoryRepository;

    @Autowired
    private SynchronizationStateRepository synchronizationStateRepository;

    @Bean
    @Primary
    public AppConfig appConfig(final ResourceLoader resourceLoader,
                               final ObjectMapper objectMapper,
                               @Value("${demo.demoConfig:classpath:demo-config.json}") final String demoConfigFile) throws IOException {
        final InputStream demoConfigInputStream = resourceLoader.getResource(demoConfigFile).getInputStream();
        return objectMapper.readValue(demoConfigInputStream, AppConfig.class);
    }

    @Bean
    public DemoDefinition demoDefinition(
            final ResourceLoader resourceLoader,
            final ObjectMapper objectMapper,
            @Value("${demo.demoDefinitionFile:classpath:demo-definition.json}") final String demoDefinitionFile) throws IOException {
        final InputStream demoDefinitionFileInputStream = resourceLoader.getResource(demoDefinitionFile).getInputStream();
        return objectMapper.readValue(demoDefinitionFileInputStream, DemoDefinition.class);
    }

    @PostConstruct
    private void dropCurrentDbState() {
        log.info("Cleaning DB for demo mode");

        log.debug("Dropping synchronization state");
        synchronizationStateRepository.deleteAll();

        log.debug("Dropping projects history");
        projectHistoryRepository.deleteAll();

        log.debug("Dropping projects");
        projectRepository.deleteAll();

        log.info("All persistent state dropped");
    }

}
