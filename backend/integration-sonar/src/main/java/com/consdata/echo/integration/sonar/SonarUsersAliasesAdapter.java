package com.consdata.echo.integration.sonar;

import com.consdata.echo.integration.sonar.configuration.SonarProperties;
import com.consdata.echo.organization.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SonarUsersAliasesAdapter {
    private final SonarProperties sonarProperties;

    public Set<String> userIdsWithAliases(List<User> users) {
        return users.stream()
                .map(User::id)
                .map(this::resolveAliases)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private Set<String> resolveAliases(String id) {
        Set<String> aliases = sonarProperties.userAliases()
                .stream()
                .map(pattern -> MessageFormat.format(pattern, id))
                .collect(Collectors.toSet());
        aliases.add(id);
        return aliases;
    }
}
