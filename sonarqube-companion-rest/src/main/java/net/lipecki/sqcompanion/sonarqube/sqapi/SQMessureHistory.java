package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.Date;

@Data
public class SQMessureHistory {

    private Date date;
    private Integer value;

}
