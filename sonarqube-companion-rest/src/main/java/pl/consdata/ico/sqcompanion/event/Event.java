package pl.consdata.ico.sqcompanion.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "events")
@Table(indexes = {
        @Index(name = "IDX_EVENT_GROUP_ID", columnList = "groupId"),
        @Index(name = "IDX_EVENT_PROJECT_ID", columnList = "projectId"),
        @Index(name = "IDX_EVENT_USER_ID", columnList = "userId"),
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String groupId;
    private String projectId;
    private String userId;
    private boolean global;
    private String name;
    private LocalDate date;
    private String type;
    @Column(length = Integer.MAX_VALUE)
    private String description;
    @Column(length = Integer.MAX_VALUE)
    private String data;
    @Column(length = Integer.MAX_VALUE)
    private String log;
    private boolean showOnTimeline;

    public String getDateString() {
        return date.toString();
    }
}
