package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.consdata.ico.sqcompanion.UnableToStoreAppConfigException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
public class FileAppConfigStore implements AppConfigStore {

    private final String appConfigPath;

    @Override
    public AppConfig read(ObjectMapper objectMapper, AppConfig defaultConfig) throws UnableToReadAppConfigException {
        try {
            log.info("Reading app configuration from path: {}", appConfigPath);
            File appConfigFile = Paths.get(appConfigPath).toFile();
            if (!appConfigFile.exists()) {
                log.info("App configuration not exist, creating default [path={}]", appConfigPath);
                store(objectMapper, defaultConfig);
            }
            return objectMapper.readValue(Paths.get(appConfigPath).toFile(), AppConfig.class);
        } catch (IOException | UnableToStoreAppConfigException e) {
            throw new UnableToReadAppConfigException(e);
        }
    }

    @Override
    public void store(ObjectMapper objectMapper, AppConfig appConfig) throws UnableToStoreAppConfigException {
        try {
            objectMapper.writeValue(Paths.get(appConfigPath).toFile(), appConfig);
        } catch (IOException e) {
            throw new UnableToStoreAppConfigException(e);
        }
    }
}
