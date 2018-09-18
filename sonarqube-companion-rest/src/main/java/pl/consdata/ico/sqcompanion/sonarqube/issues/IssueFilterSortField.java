package pl.consdata.ico.sqcompanion.sonarqube.issues;

public enum IssueFilterSortField implements IssueFilterQueryParam {

    CREATION_DATE("CREATION_DATE"),
    UPDATE_DATE("UPDATE_DATE"),
    CLOSE_DATE("CLOSE_DATE"),
    ASSIGNEE("ASSIGNEE"),
    SEVERITY("SEVERITY"),
    STATUS("STATUS"),
    FILE_LINE("FILE_LINE");

    private final String value;

    IssueFilterSortField(final String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

}
