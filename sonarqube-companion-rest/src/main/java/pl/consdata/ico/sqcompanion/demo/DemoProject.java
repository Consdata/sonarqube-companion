package pl.consdata.ico.sqcompanion.demo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DemoProject {

    private String key;
    private String name;
    private List<DemoIssue> issues;
    private List<DemoHistoricMeasure> history;

}
