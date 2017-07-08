package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQMessure {

    private String metric;
    private List<SQMessureHistory> history;

}
