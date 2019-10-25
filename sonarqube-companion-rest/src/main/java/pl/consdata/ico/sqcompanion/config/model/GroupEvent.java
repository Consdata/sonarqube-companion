package pl.consdata.ico.sqcompanion.config.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupEvent {

    private String uuid;
    private String type;
    private String name;
    private String description;
    @Singular("data")
    private Map<String, Object> data;

    public GroupEvent(GroupEvent event) {
        copy(event);
    }

    public void copy(GroupEvent event) {
        setUuid(event.uuid);
        setType(event.type);
        setName(event.name);
        setDescription(event.description);
        if (data != null) {
            setData(new HashMap<>(event.data));
        } else {
            setData(new HashMap<>());
        }
    }

    public enum TYPE {PERIOD, DATE}
}
