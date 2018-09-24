package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQIssuesSearchResponse extends SQPaginatedResponse {

    private List<SQIssue> issues;
    private List<SQFacet> facets;

}
