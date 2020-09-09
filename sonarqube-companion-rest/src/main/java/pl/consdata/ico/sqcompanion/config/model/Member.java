package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String firstName;
    private String lastName;
    private String mail;
    private String uuid;
    private Set<String> aliases;
    private Set<String> groups;
    private boolean remote;
    private String remoteType;
}
