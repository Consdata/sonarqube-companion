package pl.consdata.ico.sqcompanion.sonarqube;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class SonarQubeIssuesFacet {

    @Singular
    private List<Map<String, String>> values;

    public String getByProperty(final String keyProperty, final String keyValue, final String property) {
        return values.stream()
                .filter(map -> map.get(keyProperty).equals(keyValue))
                .map(map -> map.get(property))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(
                                "Can't find property for key [key=%s, keyValue=%s, property=%s]",
                                keyProperty,
                                keyValue,
                                property
                        )
                ));
    }

}
