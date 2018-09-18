package pl.consdata.ico.sqcompanion.sonarqube.issues;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class IssueFilter {

    // Comma-separated list of the optional fields to be returned in response. Action plans are dropped in 5.5, it is not returned in the response.
    // _all, comments, languages, actionPlans, rules, transitions, actions, users
    // additionalFields
    @Singular
    private List<IssueFilterAdditionalFields> additionalFields;

    // Ascending sort
    // false, yes, no
    // asc
    private Boolean asc;

    // To retrieve assigned or unassigned issues
    // false, yes, no
    // assigned
    private Boolean assigned;

    // Comma-separated list of assignee logins.
    // assignees
    @Singular
    private List<String> assignees;

    // Comma-separated list of SCM accounts
    // authors
    @Singular
    private List<String> authors;

    // Comma-separated list of component keys. Retrieve issues associated to a specific list of components (and all its descendants). A component can be a portfolio, project, module, directory or file.
    // componentKeys
    @Singular
    private List<String> componentKeys;

    // To retrieve issues created after the given date (inclusive). <br>Either a date (server timezone) or datetime can be provided. <br>If this parameter is set, createdSince must not be set
    // 2017-10-19 or 2017-10-19T13:00:00+0200
    // createdAfter
    private LocalDate createdAfter;

    // To retrieve issues created before the given date (inclusive). <br>Either a date (server timezone) or datetime can be provided.
    // 2017-10-19 or 2017-10-19T13:00:00+0200
    // createdBefore
    private LocalDate createdBefore;

    // Comma-separated list of resolutions
    // FALSE-POSITIVE, WONTFIX, FIXED, REMOVED
    // resolutions
    @Singular
    private List<IssueFilterResolution> resolutions;

    // To match resolved or unresolved issues
    // false, yes, no
    // resolved
    private Boolean resolved;

    // Sort field
    // CREATION_DATE, UPDATE_DATE, CLOSE_DATE, ASSIGNEE, SEVERITY, STATUS, FILE_LINE
    // s
    private IssueFilterSortField sort;

    // ps
    private Integer limit;

    public Optional<String> query() {
        final List<String> parts = new ArrayList<>();

        parts.addAll(part(additionalFields, queryParams("additionalFields")));
        parts.addAll(part(asc, yesNo("asc")));
        parts.addAll(part(assigned, yesNo("assigned")));
        parts.addAll(part(assignees, strings("assignees")));
        parts.addAll(part(authors, strings("authors")));
        parts.addAll(part(componentKeys, strings("componentKeys")));
        parts.addAll(part(resolved, yesNo("resolved")));
        parts.addAll(part(resolutions, queryParams("resolutions")));
        parts.addAll(part(sort, queryParam("s")));
        parts.addAll(part(limit, l -> l.toString()));

        if (parts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                    parts.stream().collect(
                            Collectors.joining("&")
                    )
            );
        }
    }

    private <T> List<String> part(final T value, final Function<T, String> mapper) {
        return Optional
                .ofNullable(value)
                .map(mapper)
                .map(e -> Collections.singletonList(e))
                .orElse(Collections.emptyList());
    }

    private Function<Boolean, String> yesNo(final String name) {
        return a -> String.format("%s=%s", name, a ? "yes" : "no");
    }

    private Function<List<String>, String> strings(final String name) {
        return a -> String.format("%s=%s", name, a.stream().collect(Collectors.joining(",")));
    }

    private Function<IssueFilterQueryParam, String> queryParam(final String name) {
        return a -> String.format("%s=%s", name, a.value());
    }

    private Function<List<? extends IssueFilterQueryParam>, String> queryParams(final String name) {
        return a -> String.format("%s=%s", name, a.stream().map(param -> param.value()).collect(Collectors.joining(",")));
    }

}
