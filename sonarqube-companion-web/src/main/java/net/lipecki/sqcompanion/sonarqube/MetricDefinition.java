package net.lipecki.sqcompanion.sonarqube;

public class MetricDefinition {

    public MetricDefinition(final String key) {
        this.key = key;
    }

    public static MetricDefinition of(final String key) {
        return new MetricDefinition(key);
    }

    private final String key;

    public String getKey() {
        return key;
    }

}
