package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.hook.action.NoImprovementWebhookActionData;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.textOfBlankNode;

public class NoImprovementWebhookActionDataDeserializer extends StdDeserializer<NoImprovementWebhookActionData> {

    public NoImprovementWebhookActionDataDeserializer() {
        this(null);
    }

    protected NoImprovementWebhookActionDataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public NoImprovementWebhookActionData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return NoImprovementWebhookActionData.builder()
                .period(textOfBlankNode(node.get("period"), EMPTY))
                .severity(mapper.convertValue(node.get("severity"), new TypeReference<List<String>>() {
                }))
                .build();

    }
}
