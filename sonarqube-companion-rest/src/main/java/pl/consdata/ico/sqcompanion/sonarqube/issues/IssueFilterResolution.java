package pl.consdata.ico.sqcompanion.sonarqube.issues;

public enum IssueFilterResolution implements IssueFilterQueryParam {

    FALSE_POSITIVE("FALSE-POSITIVE"),
    WONTFIX("WONTFIX"),
    FIXED("FIXED"),
    REMOVED("REMOVED");

    private final String value;

    IssueFilterResolution(final String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

}
