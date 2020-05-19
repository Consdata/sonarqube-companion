package pl.consdata.ico.sqcompanion.config.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MembersDefinition {
    private List<MemberIntegration> integrations;
    private List<Member> local = new ArrayList<>();
}
