package pl.consdata.ico.sqcompanion.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;

import java.util.List;

@Slf4j
@Service
public class UsersService {

    private final SonarQubeFacade sonarQubeFacade;

    public UsersService(final SonarQubeFacade sonarQubeFacade) {
        this.sonarQubeFacade = sonarQubeFacade;
    }

    public List<SonarQubeUser> users(final String serverId) {
        // TODO: blacklist for users
        return sonarQubeFacade.users(serverId);
    }

    public void sync() {
        // TODO: get all users and store in local repository, use login as userid
    }

}
