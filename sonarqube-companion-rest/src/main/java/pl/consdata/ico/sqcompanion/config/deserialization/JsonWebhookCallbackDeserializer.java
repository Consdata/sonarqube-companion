package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.lang.StringUtils;
import pl.consdata.ico.sqcompanion.hook.callback.JSONWebhookCallback;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class JsonWebhookCallbackDeserializer extends StdDeserializer<JSONWebhookCallback> {

    public JsonWebhookCallbackDeserializer() {
        this(null);
    }

    protected JsonWebhookCallbackDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public JSONWebhookCallback deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return JSONWebhookCallback.builder()
                .type(DeserializationUtil.textOfNullable(node.get("type"), "POST"))
                .uuid(DeserializationUtil.generateUuidIfRequired(node.get("uuid")))
                .name(Optional.ofNullable(node.get("name")).orElse(new TextNode(StringUtils.EMPTY)).asText())
                .body(mapper.convertValue(node.get("body"), new TypeReference<Map<String, String>>() {
                }))
                .build();

    }
}
