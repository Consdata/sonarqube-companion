package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

import java.util.List;

@Data
public class SQIssuesSearchResponse extends SQPaginatedResponse {

    private List<SQIssue> issues;

}
