package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.GroupEvent;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.*;

public class GroupEventDeserializer extends StdDeserializer<GroupEvent> {

    public GroupEventDeserializer() {
        this(null);
    }

    private GroupEventDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public GroupEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return GroupEvent.builder()
                .uuid(generateUuidIfRequired(node.get("uuid")))
                .type(textOfNullable(node.get("type"), EMPTY))
                .name(textOfNullable(node.get("name"), EMPTY))
                .data(mapOfNullable(node.get("data"), mapper))
                .description(textOfNullable(node.get("description"), EMPTY))
                .build();
    }
}
