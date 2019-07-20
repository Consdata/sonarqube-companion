package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.ProjectLink;
import pl.consdata.ico.sqcompanion.config.model.ProjectLinkType;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.*;

public class ProjectLinkDeserializer extends StdDeserializer<ProjectLink> {

    public ProjectLinkDeserializer() {
        this(null);
    }

    protected ProjectLinkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProjectLink deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return ProjectLink.builder()
                .uuid(generateUuidIfRequired(node.get("uuid")))
                .serverId(textOfNullable(node.get("serverId"), EMPTY))
                .type(ProjectLinkType.valueOf(textOfNullable(node.get("type"), "DIRECT")))
                .config(mapOfNullable(node.get("config"), mapper))
                .build();
    }
}
