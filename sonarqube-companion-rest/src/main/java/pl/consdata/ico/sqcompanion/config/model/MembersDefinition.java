package pl.consdata.ico.sqcompanion.config.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembersDefinition {
    @Singular("local")
    private List<Member> local = new ArrayList<>();
    private boolean recursive;
}
