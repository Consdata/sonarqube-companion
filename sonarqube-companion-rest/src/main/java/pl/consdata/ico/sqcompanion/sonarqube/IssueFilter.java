package pl.consdata.ico.sqcompanion.sonarqube;


import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;

@Data
@Builder
public class IssueFilter {

    private static final String AUTHORS = "authors";
    private static final String RESOLVED = "resolved";
    private static final String COMPONENT_KEYS = "componentKeys";
    private static final String CREATED_AFTER = "createdAfter";


    @Singular
    private List<String> authors;
    private boolean resolved;
    @Singular
    private List<String> projectKeys;
    private String createdAfter;

    public String query() {
        return addCondition(AUTHORS, authors, true)
                + addCondition(RESOLVED, String.valueOf(resolved), false)
                + addCondition(COMPONENT_KEYS, projectKeys, false)
                + addCondition(CREATED_AFTER, createdAfter, false);
    }

    private String addCondition(String name, Collection<String> values, boolean first) {
        String prefix = first ? "?" : "&";
        if (!values.isEmpty()) {
            return prefix + name + "=" + String.join(",", values);
        } else {
            return EMPTY;
        }
    }

    private String addCondition(String name, String value, boolean first) {
        String prefix = first ? "?" : "&";
        if (!StringUtils.isBlank(value)) {
            return prefix + name + "=" + value;
        } else {
            return EMPTY;
        }
    }
}
