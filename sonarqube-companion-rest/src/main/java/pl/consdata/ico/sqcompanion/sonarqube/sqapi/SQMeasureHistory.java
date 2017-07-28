package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.Date;

@Data
public class SQMeasureHistory {

    private Date date;
    private Integer value;

}
