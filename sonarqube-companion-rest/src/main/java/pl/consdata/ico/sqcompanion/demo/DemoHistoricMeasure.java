package pl.consdata.ico.sqcompanion.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DemoHistoricMeasure {

    private String relativeDate;
    private int blockers;
    private int criticals;
    private int majors;
    private int minors;
    private int infos;

}
