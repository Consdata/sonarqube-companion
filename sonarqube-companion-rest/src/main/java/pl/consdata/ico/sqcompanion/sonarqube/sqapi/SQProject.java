package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class SQProject {

    private String key;
    private String name;
    private String creationDate;
    private String visibility;

}
