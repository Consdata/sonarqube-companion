package pl.consdata.ico.sqcompanion.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity(name = "projects")
@Table(
        indexes = {
                @Index(name = "IDX_PROJECTS_KEY", columnList = "key"),
                @Index(name = "IDX_PROJECTS_SERVER_ID", columnList = "serverId")
        }
)
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;
    private String name;
    private String serverId;

}
