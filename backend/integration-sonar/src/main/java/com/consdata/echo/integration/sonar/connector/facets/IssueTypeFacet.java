package com.consdata.echo.integration.sonar.connector.facets;

public record IssueTypeFacet(
        long codeSmell,
        long vulnerabilities,
        long bugs
){
}
