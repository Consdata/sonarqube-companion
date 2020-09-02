package pl.consdata.ico.sqcompanion.config.git;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class GitConfiguration {

    @Value("${git.repo:}")
    private String repoUrl;

    @Value("${git.path:}")
    private String configPath;

    @Value("${git.workspace:}")
    private String workspace;

    @Value("${git.message:SQC}")
    private String message;

    @Value("${git.branch:}")
    private String branch;

    @Bean
    @ConditionalOnProperty(value = "config.store", havingValue = "git")
    public GitAppConfigStore gitIntegration() throws UnableToCloneRepoException {
        try {
            FileUtils.deleteDirectory(Paths.get(workspace).toFile());
            Git git = Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(Paths.get(workspace).toFile())
                    .call();
            String workBranch = branch;
            if (StringUtils.isNotBlank(branch)) {
                git.checkout().setCreateBranch(false).setName(branch).call();
            } else {
                workBranch = "SQC-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"));
                git.checkout().setCreateBranch(true).setName(workBranch).call();
            }
            return new GitAppConfigStore(git, configPath, workBranch, message);
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
            throw new UnableToCloneRepoException();
        }
    }
}
