package pl.consdata.ico.sqcompanion.ext;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.consdata.ico.sqcompanion.ext.ranking.RankingExtension;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RankingExtension.class, name = "ranking"),
})
public interface Extension {

}
