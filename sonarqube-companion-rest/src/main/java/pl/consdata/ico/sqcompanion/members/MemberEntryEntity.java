package pl.consdata.ico.sqcompanion.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntryEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String mail;

    @ElementCollection
    private Set<String> aliases = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "member"
    )
    private List<MembershipEntryEntity> membershipEvents = new ArrayList<>();

}
