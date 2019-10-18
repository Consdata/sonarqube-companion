package pl.consdata.ico.sqcompanion.config.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.cache.Caches;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.validation.ValidationResult;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static pl.consdata.ico.sqcompanion.config.validation.ValidationResult.invalid;
import static pl.consdata.ico.sqcompanion.config.validation.ValidationResult.valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsService {

    private static final String BACKUP_SUFFIX = "_backup";

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;
    private final RepositoryService repositoryService;

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

    private void copy(String src, String dst) {
        Path srcPath = Paths.get(src);
        Path dstPath = Paths.get(dst);
        try {
            Files.copy(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Unable to copy config from {} to {}", src, dst, e);
        }
    }


    @PostConstruct
    public void backupDefaultSettings() {
        copy(appConfigFile, appConfigFile + BACKUP_SUFFIX);
    }

    public AppConfig getConfig() {
        return appConfig;
    }

    public ValidationResult save() {
        if (store()) {
            return valid();
        } else {
            return invalid("STORE", "Cannot save configuration");
        }
    }

    private boolean store() {
        try {
            objectMapper.writeValue(Paths.get(appConfigFile).toFile(), appConfig);
            //TODO resync and clear only new elements
            repositoryService.syncGroups();
            Caches.LIST
                    .stream()
                    .map(cacheManager::getCache)
                    .forEach(Cache::clear);
            return true;
        } catch (IOException e) {
            log.error("Unable to store configuration in {}", this.appConfigFile, e);
            return false;
        }
    }
}
