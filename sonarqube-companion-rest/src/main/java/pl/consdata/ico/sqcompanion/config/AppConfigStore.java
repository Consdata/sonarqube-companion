package pl.consdata.ico.sqcompanion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.consdata.ico.sqcompanion.UnableToStoreAppConfigException;

public interface AppConfigStore {
    AppConfig read(ObjectMapper objectMapper, AppConfig defaultConfig) throws UnableToReadAppConfigException;

    void store(ObjectMapper objectMapper, AppConfig appConfig) throws UnableToStoreAppConfigException;
}
