package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.*;

import java.util.Map;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InMemoryRepository {

    @Singular
    private Map<String, InMemoryProject> projects;

    @Singular
    private Map<String, SonarQubeUser> users;

}
