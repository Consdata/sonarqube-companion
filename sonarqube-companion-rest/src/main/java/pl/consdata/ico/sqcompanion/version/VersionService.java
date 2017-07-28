package pl.consdata.ico.sqcompanion.version;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author pogoma
 */
@Service
@Slf4j
public class VersionService {

    private static final String MANIFEST_FILENAME = "META-INF/MANIFEST.MF";
    private static final String IMPLEMENTATION_VERSION = "Implementation-Version";
    private static final String BUILD_TIMESTAMP = "Build-Timestamp";
    private static final String COMMIT_ID = "Commit-Id";
    private static final String BRANCH = "Branch";
    private static final String DEFAULT_ENTRY_VALUE = "UNKNOWN";
    private ApplicationVersion applicationVersion;
    private Properties manifest;

    public ApplicationVersion getApplicationVersion() {
        return applicationVersion;
    }

    @PostConstruct
    private void readManifest() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(MANIFEST_FILENAME);
        manifest = new Properties();
        try {
            manifest.load(is);
            applicationVersion = ApplicationVersion.builder()
                    .version(getProperty(IMPLEMENTATION_VERSION))
                    .buildTimestamp(getProperty(BUILD_TIMESTAMP))
                    .commitId(getProperty(COMMIT_ID))
                    .branch(getProperty(BRANCH))
                    .build();
        } catch (IOException ex) {
            log.warn(ex.getMessage(), ex);
        }
    }

    private String getProperty(String name) {
        return manifest.getProperty(name, DEFAULT_ENTRY_VALUE);
    }

}
