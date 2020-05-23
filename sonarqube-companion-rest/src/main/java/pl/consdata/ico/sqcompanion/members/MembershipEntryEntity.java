package pl.consdata.ico.sqcompanion.members;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "membership_entries")
@Table(indexes = {
        @Index(name = "IDX_MEMBERSHIP_GROUP_ID", columnList = "groupId"),
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipEntryEntity {
    @Id
    @GeneratedValue
    private Long membershipId;
    private String groupId;
    private LocalDate date;
    private Event event;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private MemberEntryEntity member;

    public enum Event {
        ATTACHED, DETACHED
    }
}
