package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.ServerDefinition;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isBlank;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;

public class ServerDefinitionDeserializer extends StdDeserializer<ServerDefinition> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultDeserializer;

    public ServerDefinitionDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(ServerDefinition.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public ServerDefinition deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        ServerDefinition serverDefinition = (ServerDefinition) defaultDeserializer.deserialize(jsonParser, context);
        serverDefinition.setUuid(generateUuidIfRequired(serverDefinition.getUuid()));
        serverDefinition.setId(isBlank(serverDefinition.getId()) ? serverDefinition.getUuid() : serverDefinition.getId());
        return serverDefinition;
    }


    @Override
    public void resolve(DeserializationContext context) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(context);
    }
}


