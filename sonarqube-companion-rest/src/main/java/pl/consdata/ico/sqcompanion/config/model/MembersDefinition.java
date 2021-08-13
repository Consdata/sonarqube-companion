package pl.consdata.ico.sqcompanion.config.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MembersDefinition {
    @Singular("local")
    private List<Member> local = new ArrayList<>();
}
