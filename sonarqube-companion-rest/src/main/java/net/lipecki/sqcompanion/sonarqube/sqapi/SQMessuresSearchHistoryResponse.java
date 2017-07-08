package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQMessuresSearchHistoryResponse extends SQPaginatedResponse {

    private List<SQMessure> measures;

}
