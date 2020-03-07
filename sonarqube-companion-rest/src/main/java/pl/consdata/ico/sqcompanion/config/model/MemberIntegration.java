package pl.consdata.ico.sqcompanion.config.model;

import lombok.Data;

import java.util.Map;

@Data
public class MemberIntegration {
    private String type;
    private Map<String, String> params;
}
