package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;

import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.textOfBlankNode;

public class PostWebhookCallbackDeserializer extends StdDeserializer<PostWebhookCallback> {

    public PostWebhookCallbackDeserializer() {
        this(null);
    }

    protected PostWebhookCallbackDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PostWebhookCallback deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return PostWebhookCallback.builder()
                .uuid(generateUuidIfRequired(node.get("uuid")))
                .name(textOfBlankNode(node.get("name"), EMPTY))
                .body(mapper.convertValue(node.get("body"), new TypeReference<Map<String, String>>() {
                }))
                .url(textOfBlankNode(node.get("url"), EMPTY))
                .build();


    }
}
