package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.ServerAuthentication;

import java.io.IOException;

import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.mapOfNullable;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.textOfBlankNode;

public class ServerAuthenticationDeserializer extends StdDeserializer<ServerAuthentication> {

    public ServerAuthenticationDeserializer() {
        this(null);
    }

    protected ServerAuthenticationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ServerAuthentication deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return ServerAuthentication.builder()
                .type(textOfBlankNode(node.get("type"), "none"))
                .params(mapOfNullable(node.get("params"), mapper))
                .build();
    }
}