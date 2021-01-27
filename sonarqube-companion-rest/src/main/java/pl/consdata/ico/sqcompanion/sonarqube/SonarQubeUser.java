package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SonarQubeUser {

    private String userId;
    private String login;
    private boolean active;
    private Set<String> aliases;

}
