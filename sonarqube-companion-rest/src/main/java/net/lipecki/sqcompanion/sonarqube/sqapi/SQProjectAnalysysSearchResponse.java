package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQProjectAnalysysSearchResponse extends SQPaginatedResponse {

    private List<SQProjectAnalyse> analyses;

}
