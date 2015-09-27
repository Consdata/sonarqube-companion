package net.lipecki.sqcompanion.repository;

/**
 * Created by gregorry on 26.09.2015.
 */
public enum IssueSeverity {

    BLOCKER(5, true),

    CRITICAL(4, true),

    MAJOR(3, false),

    MINOR(2, false),

    INFO(1, false);

    private final int code;
    private final boolean siginificant;

    IssueSeverity(final int code, final boolean siginificant) {
        this.code = code;
        this.siginificant = siginificant;
    }

    public static IssueSeverity parse(final String severityString) {
        if (severityString != null) {
            for (final IssueSeverity severity : IssueSeverity.values()) {
                if (severity.name().equalsIgnoreCase(severityString)) {
                    return severity;
                }
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public boolean isSignificant() {
        return siginificant;
    }

}
