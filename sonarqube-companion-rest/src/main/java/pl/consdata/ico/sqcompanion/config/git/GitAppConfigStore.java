package pl.consdata.ico.sqcompanion.config.git;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RefSpec;
import pl.consdata.ico.sqcompanion.UnableToStoreAppConfigException;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.config.AppConfigStore;
import pl.consdata.ico.sqcompanion.config.UnableToReadAppConfigException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
public class GitAppConfigStore implements AppConfigStore {

    private final Git git;
    private final String path;
    private final String origin;
    private final String branch;
    private final String message;

    @Override
    public AppConfig read(ObjectMapper objectMapper, AppConfig defaultConfig) throws UnableToReadAppConfigException {
        try {
            log.info("Reading app configuration from branch: {}", branch);
            File appConfigFile = Paths.get(git.getRepository().getWorkTree().getAbsolutePath(), path).toFile();
            if (!appConfigFile.exists()) {
                log.info("App configuration not exist on branch, creating default");
                store(objectMapper, defaultConfig);
            }
            return objectMapper.readValue(appConfigFile, AppConfig.class);
        } catch (IOException | UnableToStoreAppConfigException e) {
            throw new UnableToReadAppConfigException(e);
        }
    }

    @Override
    public void store(ObjectMapper objectMapper, AppConfig appConfig) throws UnableToStoreAppConfigException {
        try {
            File appConfigFile = Paths.get(git.getRepository().getWorkTree().getAbsolutePath(), path).toFile();
            objectMapper.writeValue(appConfigFile, appConfig);
            git.add().addFilepattern(".").call();
            git.commit().setMessage(message).call();
            git.push().setRemote(origin).setRefSpecs(new RefSpec(branch + ":" + branch)).call();
        } catch (GitAPIException | IOException e) {
            log.error("Unable to store config", e);
            throw new UnableToStoreAppConfigException(e);
        }
    }
}
