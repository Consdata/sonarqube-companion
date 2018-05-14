package pl.consdata.ico.sqcompanion.widget;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import pl.consdata.ico.sqcompanion.widget.ranking.RankingWidget;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RankingWidget.class, name = "ranking"),
})
@Data
public abstract class Widget {
    private String title;
    private String type;
}
