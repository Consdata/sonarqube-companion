package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.consdata.ico.sqcompanion.UnableToStoreAppConfigException;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;
import pl.consdata.ico.sqcompanion.config.model.SchedulerConfig;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileAppConfigStoreTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    FileAppConfigStore appConfigStore;

    @Test
    public void shouldCreateDefaultConfig() throws UnableToReadAppConfigException, UnableToStoreAppConfigException {
        //given
        File configFile = Paths.get(folder.getRoot().getAbsolutePath(), "test").toFile();
        assertThat(configFile).doesNotExist();
        appConfigStore = new FileAppConfigStore(configFile.toString());

        //when
        AppConfig appConfig = appConfigStore.read(new ObjectMapper(), defaultConfig());

        //then
        assertThat(configFile).exists();
        assertThat(appConfig.getGroup("test")).isNotNull();
    }


    private AppConfig defaultConfig() {
        return AppConfig
                .builder()
                .scheduler(new SchedulerConfig(1L, TimeUnit.DAYS))
                .servers(new ArrayList<>())
                .rootGroup(
                        GroupDefinition
                                .builder()
                                .uuid("test")
                                .name("Default")
                                .build()
                )
                .build();
    }
}