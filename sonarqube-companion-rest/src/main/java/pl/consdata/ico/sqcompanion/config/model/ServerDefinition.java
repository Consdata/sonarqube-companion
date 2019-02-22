package pl.consdata.ico.sqcompanion.config.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ServerDefinition {

    private String id;
    private String url;
    private ServerAuthentication authentication;
    private List<String> blacklistUsers;

    public boolean hasAuthentication() {
        return authentication != null
                && authentication.getType() != null
                && !authentication.getType().isEmpty();
    }

    public List<String> getBlacklistUsers() {
        return blacklistUsers != null ? blacklistUsers : new ArrayList<>();
    }

}
