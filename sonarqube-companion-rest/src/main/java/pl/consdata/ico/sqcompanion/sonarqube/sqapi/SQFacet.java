package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SQFacet {

    private String property;
    private List<Map<String, String>> values;

}
