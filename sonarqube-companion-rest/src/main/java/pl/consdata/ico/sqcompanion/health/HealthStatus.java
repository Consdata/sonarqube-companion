package pl.consdata.ico.sqcompanion.health;

/**
 * @author gregorry
 */
public enum HealthStatus {

    UNKNOWN,
    HEALTHY,
    WARNING,
    UNHEALTHY;

    public int getPriority() {
        return ordinal();
    }

}
