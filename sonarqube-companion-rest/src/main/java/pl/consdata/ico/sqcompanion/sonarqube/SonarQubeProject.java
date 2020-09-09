package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SonarQubeProject {

    private String key;
    private String name;

}
