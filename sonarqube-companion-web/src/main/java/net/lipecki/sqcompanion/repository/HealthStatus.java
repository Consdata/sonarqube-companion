package net.lipecki.sqcompanion.repository;

/**
 * Created by gregorry on 26.09.2015.
 */
public enum HealthStatus {

    BLOCKER(5),
    CRITICAL(4),
    HEALTHY(0);

    private final int code;

    HealthStatus(final int code) {
        this.code = code;
    }

    public static HealthStatus of(final Issues issues) {
        if (!issues.getBlockers().isEmpty()) {
            return BLOCKER;
        } else if (!issues.getCriticals().isEmpty()) {
            return CRITICAL;
        } else {
            return HEALTHY;
        }
    }

    public int getCode() {
        return code;
    }
    
}
