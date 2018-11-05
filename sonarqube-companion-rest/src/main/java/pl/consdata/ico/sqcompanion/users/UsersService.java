package pl.consdata.ico.sqcompanion.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsersService {

    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;

    public UsersService(final SonarQubeFacade sonarQubeFacade, AppConfig appConfig) {
        this.sonarQubeFacade = sonarQubeFacade;
        this.appConfig = appConfig;
    }

    public List<SonarQubeUser> users(final String serverId) {
        final List<String> blacklist = appConfig.getServer(serverId).getBlacklistUsers();
        final List<SonarQubeUser> users = sonarQubeFacade.users(serverId)
                .stream()
                .filter(user -> blacklist.stream().noneMatch(blacklisted -> user.getUserId().matches(blacklisted)))
                .collect(Collectors.toList());
        return users;
    }

    public void sync() {
        // TODO: get all users and store in local repository, use login as userid
    }

}
