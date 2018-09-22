package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * @author gregorry
 */
@Data
@Builder
public class InMemoryRepository {

    @Singular
    private Map<String, InMemoryProject> projects;

    @Singular
    private Map<String, SonarQubeUser> users;

}
