package pl.consdata.ico.sqcompanion.config.git;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "git")
public class GitProperties {
    private String repo;
    private String branch;
    private String remote = "origin";
    private String path;
    private String workspace;
    private String message = "SQC";
    private String identityPath;

}
