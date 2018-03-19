package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SQUser {
    private String login;
    private String name;
    private boolean active;
    private String email;

}
