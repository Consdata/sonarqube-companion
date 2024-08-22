package com.consdata.echo.integration.sonar.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class SqSearchIssuesRequest {

    private String serverId;
    private int pageSize;
    private int page;
    private LocalDate from;
    private LocalDate to;
    private String sort;
    private String status;
    private Boolean resolved;
    private String project;
    private String user;
    private List<String> facets;

}
