package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.lang.StringUtils;
import pl.consdata.ico.sqcompanion.hook.callback.PostWebhookCallback;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

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
                .type(DeserializationUtil.textOfNullable(node.get("type"), "POST"))
                .uuid(DeserializationUtil.generateUuidIfRequired(node.get("uuid")))
                .name(Optional.ofNullable(node.get("name")).orElse(new TextNode(StringUtils.EMPTY)).asText())
                .body(mapper.convertValue(node.get("body"), new TypeReference<Map<String, String>>() {
                }))
                .url(Optional.ofNullable(node.get("url")).orElse(new TextNode(StringUtils.EMPTY)).asText())
                .build();


    }
}
