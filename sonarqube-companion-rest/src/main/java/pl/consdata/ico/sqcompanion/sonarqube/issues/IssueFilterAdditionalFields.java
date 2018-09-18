package pl.consdata.ico.sqcompanion.sonarqube.issues;

public enum IssueFilterAdditionalFields implements IssueFilterQueryParam {

    ALL("_all"),
    COMMENTS("comments"),
    LANGUAGES("languages"),
    ACTION_PLANES("actionPlans"),
    RULES("rules"),
    TRANSITIONS("transitions"),
    ACTIONS("actions"),
    USERS("users");

    private final String value;

    IssueFilterAdditionalFields(final String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

}
