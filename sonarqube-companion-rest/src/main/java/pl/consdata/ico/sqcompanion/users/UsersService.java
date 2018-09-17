package pl.consdata.ico.sqcompanion.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
// TODO: implement!
public class UsersService {

    private final SonarQubeFacade sonarQubeFacade;

    public UsersService(final SonarQubeFacade sonarQubeFacade) {
        this.sonarQubeFacade = sonarQubeFacade;
    }

    public List<User> users() {
        sonarQubeFacade.users(serverId);
        return new ArrayList<>();
    }

    public void sync() {
        // TODO: get all users and store in local repository, use login as userid
    }

}
