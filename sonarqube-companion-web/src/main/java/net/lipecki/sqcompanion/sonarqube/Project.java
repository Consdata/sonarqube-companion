package net.lipecki.sqcompanion.sonarqube;

public class Project {

    private final String key;

    private Project(final String key) {
        this.key = key;
    }

    public static Project of(final String key) {
        return new Project(key);
    }

    public String getKey() {
        return key;
    }

}
