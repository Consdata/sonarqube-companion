package pl.consdata.ico.sqcompanion.config.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServerDefinition {

    private String id;
    private String uuid;
    private String url;
    private ServerAuthentication authentication;
    private List<String> blacklistUsers;
    private List<String> aliases;

    public boolean hasAuthentication() {
        return authentication != null
                && authentication.getType() != null
                && !authentication.getType().isEmpty()
                && !authentication.getType().toLowerCase().equals("none");
    }

    public List<String> getBlacklistUsers() {
        return blacklistUsers != null ? blacklistUsers : new ArrayList<>();
    }

}
