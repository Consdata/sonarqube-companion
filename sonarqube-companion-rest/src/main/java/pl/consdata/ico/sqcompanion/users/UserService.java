package pl.consdata.ico.sqcompanion.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.sonarqube.RemoteSonarQubeFacade;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private RemoteSonarQubeFacade remoteSonarQubeFacade;

    public UserService(RemoteSonarQubeFacade remoteSonarQubeFacade) {
        this.remoteSonarQubeFacade = remoteSonarQubeFacade;
    }

    public List<String> getActiveUsersEmails(String serverId) {
        return remoteSonarQubeFacade.getUsers(serverId, false);
    }
}
