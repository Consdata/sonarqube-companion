package pl.consdata.ico.sqcompanion.config.git;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.util.FS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;


@Configuration
@ConditionalOnProperty(value = "config.store", havingValue = "git")
public class GitConfiguration {

    private JschConfigSessionFactory configSessionFactory(GitProperties gitProperties) {
        return new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                // do nothing
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch jsch = super.createDefaultJSch(fs);
                if (StringUtils.isNotBlank(gitProperties.getIdentityPath())) {
                    jsch.addIdentity(gitProperties.getIdentityPath());
                }
                return jsch;
            }
        };
    }

    @Bean
    public GitAppConfigStore gitIntegration(GitProperties gitProperties) throws UnableToCloneRepoException {
        try {
            FileUtils.deleteDirectory(Paths.get(gitProperties.getWorkspace()).toFile());
            JschConfigSessionFactory configSessionFactory = configSessionFactory(gitProperties);
            Git git = Git.cloneRepository()
                    .setURI(gitProperties.getRepo())
                    .setDirectory(Paths.get(gitProperties.getWorkspace()).toFile())
                    .setBranch(gitProperties.getBranch())
                    .setTransportConfigCallback(transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(configSessionFactory);
                    })
                    .call();
            return new GitAppConfigStore(git, gitProperties, configSessionFactory);
        } catch (GitAPIException | IOException e) {
            throw new UnableToCloneRepoException(e);
        }
    }
}
