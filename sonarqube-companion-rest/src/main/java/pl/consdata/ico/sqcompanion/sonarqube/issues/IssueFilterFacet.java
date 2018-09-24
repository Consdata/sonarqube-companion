package pl.consdata.ico.sqcompanion.sonarqube.issues;

import java.util.stream.Stream;

public enum IssueFilterFacet implements IssueFilterQueryParam {

    SEVERITIES("severities");

    private final String value;

    IssueFilterFacet(final String value) {
        this.value = value;
    }

    public static IssueFilterFacet ofValue(final String value) {
        return Stream.of(IssueFilterFacet.values())
                .filter(f -> f.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find facet for name: " + value));
    }

    @Override
    public String value() {
        return value;
    }

}
