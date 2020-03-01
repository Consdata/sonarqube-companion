package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDefinition {
    private String firstName;
    private String lastName;
    private String mail;
    private String uuid;
    private String sonarId;
    private List<String> aliases;
    private List<String> memberOf;
}
