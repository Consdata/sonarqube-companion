package pl.consdata.ico.sqcompanion.config.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Service
public class SettingsService {

    private static final String BACKUP_SUFFIX = "_backup";

    private AppConfig appConfig;
    private ObjectMapper objectMapper;

    @Value("${app.configFile:sq-companion-config.json}")
    private String appConfigFile;

    private SettingsService(final AppConfig appConfig, final ObjectMapper objectMapper) {
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }

    private boolean copy(String src, String dst) {
        Path srcPath = Paths.get(src);
        Path dstPath = Paths.get(dst);
        try {
            Files.copy(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Unable to copy config from {} to {}", src, dst, e);
            return false;
        }
        return true;
    }

    public GroupDefinition getGroup(String uuid, List<GroupDefinition> groups) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getUuid().equals(uuid)) {
                return groups.get(i);
            } else {
                GroupDefinition definition = getGroup(uuid, groups.get(i).getGroups());
                if (definition != null) {
                    return definition;
                }
            }
        }
        return null;
    }

    public void setGroup(String uuid, GroupDefinition groupDefinition) {

    }

    @PostConstruct
    public void backupDefaultSettings() {
        copy(appConfigFile, appConfigFile + BACKUP_SUFFIX);
    }

    public boolean restoreDefaultConfig() {
        return copy(appConfigFile + BACKUP_SUFFIX, appConfigFile);
    }

    public AppConfig getConfig() {
        return appConfig;
    }

    public boolean store() {
        try {
            objectMapper.writeValue(Paths.get(appConfigFile).toFile(), appConfig);
            return true;
        } catch (IOException e) {
            log.error("Unable to store configuration in {}", this.appConfigFile, e);
            return false;
        }
    }
}
