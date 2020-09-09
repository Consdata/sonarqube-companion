package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

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
public class SQProject {

    private String key;
    private String name;
    private String creationDate;
    private String visibility;

}
