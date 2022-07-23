package pl.consdata.ico.sqcompanion.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeFacade;
import pl.consdata.ico.sqcompanion.sonarqube.SonarQubeUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class UsersService {

    private static final Pattern VAR_PATTERN = Pattern.compile("\\{(.*?)}");
    private final SonarQubeFacade sonarQubeFacade;
    private final AppConfig appConfig;

    public UsersService(final SonarQubeFacade sonarQubeFacade, AppConfig appConfig) {
        this.sonarQubeFacade = sonarQubeFacade;
        this.appConfig = appConfig;
    }

    public List<SonarQubeUser> users(final String serverId) {
        final List<String> blacklist = appConfig.getServer(serverId).getBlacklistUsers();
        return sonarQubeFacade.users(serverId)
                .stream()
                .filter(user -> blacklist.stream().noneMatch(blacklisted -> user.getUserId().matches(blacklisted)))
                .map(user -> resolveAliases(serverId, user))
                .collect(Collectors.toList());
    }

    private SonarQubeUser resolveAliases(String serverId, SonarQubeUser user) {
        Set<String> aliases = ofNullable(appConfig.getServer(serverId).getAliases()).orElse(new ArrayList<>())
                .stream()
                .map(alias -> resolveVariables(user, alias))
                .collect(Collectors.toSet());
        aliases.add(user.getUserId());
        user.setAliases(aliases);
        return user;
    }

    private String resolveVariables(SonarQubeUser user, String alias) {
        Matcher matcher = VAR_PATTERN.matcher(alias);
        if (matcher.find() && matcher.group(1).equalsIgnoreCase("login")) {
            return alias.replace(matcher.group(0), user.getLogin());
        }
        return alias;
    }

    public void sync() {
        // TODO: get all users and store in local repository, use login as userid
    }

}
