package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQMeasuresSearchHistoryResponse extends SQPaginatedResponse {

    private List<SQMeasure> measures;

}
