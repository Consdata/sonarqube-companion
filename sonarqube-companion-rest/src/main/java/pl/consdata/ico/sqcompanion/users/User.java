package pl.consdata.ico.sqcompanion.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String login;
    private String email;
    private String name;
    private String avatar;

    // TODO: jeśli zajdzie potrzeba
    // @Singular
    // private List<String> aliases;

}
