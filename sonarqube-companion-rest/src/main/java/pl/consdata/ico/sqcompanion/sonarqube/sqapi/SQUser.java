package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SQUser {

    private String login;
    private String name;
    private boolean active;
    private String email;
    private List<String> groups;
    private int tokensCount;
    private boolean local;
    private String externalIdentity;
    private String externalProvider;
    private String avatar;

}
