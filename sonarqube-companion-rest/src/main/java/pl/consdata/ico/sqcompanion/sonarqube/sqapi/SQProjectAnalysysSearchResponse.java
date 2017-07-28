package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQProjectAnalysysSearchResponse extends SQPaginatedResponse {

    private List<SQProjectAnalyse> analyses;

}
