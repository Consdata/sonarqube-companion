package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang.StringUtils;
import pl.consdata.ico.sqcompanion.config.model.WebhookDefinition;

import java.io.IOException;

import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;

public class WebhookDeserializer extends StdDeserializer<WebhookDefinition> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultDeserializer;

    public WebhookDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(WebhookDefinition.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public WebhookDefinition deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        WebhookDefinition webhookDefinition = (WebhookDefinition) defaultDeserializer.deserialize(jsonParser, context);
        webhookDefinition.setUuid(generateUuidIfRequired(webhookDefinition.getUuid()));
        webhookDefinition.setName(StringUtils.isBlank(webhookDefinition.getName()) ? webhookDefinition.getUuid() : webhookDefinition.getName());
        return webhookDefinition;
    }


    @Override
    public void resolve(DeserializationContext context) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(context);
    }
}
