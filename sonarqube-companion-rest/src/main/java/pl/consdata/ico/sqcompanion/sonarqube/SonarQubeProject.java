package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class SonarQubeProject {

    private String key;
    private String name;

}
