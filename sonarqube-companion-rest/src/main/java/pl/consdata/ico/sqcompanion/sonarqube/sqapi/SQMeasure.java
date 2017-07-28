package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQMeasure {

    private String metric;
    private List<SQMeasureHistory> history;

}
