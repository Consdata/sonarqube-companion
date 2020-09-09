package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.*;
import pl.consdata.ico.sqcompanion.sonarqube.issues.IssueFilterFacet;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SonarQubeIssuesFacets {

    @Singular
    private Map<IssueFilterFacet, SonarQubeIssuesFacet> facets;

    public Optional<SonarQubeIssuesFacet> getFacet(final IssueFilterFacet key) {
        return Optional.ofNullable(facets.get(key));
    }

}
