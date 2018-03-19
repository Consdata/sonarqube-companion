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
    @Singular
    private List<String> authors;
    @Singular
    private List<String> statuses;
    @Singular
    private List<String> projectKeys;
    private String createdAfter;

    public String query() {
        return "?"
                + addCondition("authors", authors)
                + addCondition("statuses", statuses)
                + addCondition("projectKeys", projectKeys)
                + addCondition("createdAfter", createdAfter);
    }

    private String addCondition(String name, Collection<String> values) {
        if (!values.isEmpty()) {
            return "&" + name + "=" + String.join(",", values);
        } else {
            return EMPTY;
        }
    }

    private String addCondition(String name, String value) {
        if (!StringUtils.isBlank(value)) {
            return "&" + name + "=" + value;
        } else {
            return EMPTY;
        }
    }
}
