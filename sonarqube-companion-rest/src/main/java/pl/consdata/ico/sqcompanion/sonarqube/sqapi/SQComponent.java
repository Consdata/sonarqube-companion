package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class SQComponent {

    private String id;
    private String key;
    private String name;
    private String organization;
    private String qualifier;
    private String visibility;

}
