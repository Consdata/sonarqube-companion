package pl.consdata.ico.sqcompanion.config.model;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {
    private String firstName;
    private String lastName;
    private String mail;
    @EqualsAndHashCode.Include
    private String uuid;
    private Set<String> aliases;
    private Set<String> groups;
    private boolean remote;
    private String remoteType;

}
