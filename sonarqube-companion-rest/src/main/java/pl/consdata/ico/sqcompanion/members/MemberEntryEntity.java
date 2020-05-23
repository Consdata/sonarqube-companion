package pl.consdata.ico.sqcompanion.members;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name = "members")
@Table(indexes = {
        @Index(name = "IDX_MEMBERS_USER_ID", columnList = "userID", unique = true),
})
@Data
public class MemberEntity {
    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
}
