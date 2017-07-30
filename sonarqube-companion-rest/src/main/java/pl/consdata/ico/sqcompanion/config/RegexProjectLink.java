package pl.consdata.ico.sqcompanion.config;

import java.util.List;

public class RegexProjectLink {

    public static final String INCLUDE = "include";
    public static final String EXCLUDE = "exclude";
    private final ProjectLink projectLink;

    public RegexProjectLink(final ProjectLink projectLink) {
        this.projectLink = projectLink;
    }

    public static RegexProjectLink of(final ProjectLink projectLink) {
        return new RegexProjectLink(projectLink);
    }

    public boolean includes(final String key) {
        final List<String> includes = (List<String>) projectLink.getConfig().get(INCLUDE);
        if (includes != null) {
            return matchesAny(key, includes);
        } else {
            return false;
        }
    }

    public boolean excludes(final String key) {
        final List<String> excludes = (List<String>) projectLink.getConfig().get(EXCLUDE);
        if (excludes != null) {
            return matchesAny(key, excludes);
        } else {
            return false;
        }
    }

    private boolean matchesAny(final String key, final List<String> values) {
        return values
                .stream()
                .filter(include -> key.matches(include))
                .findAny()
                .isPresent();
    }

}
