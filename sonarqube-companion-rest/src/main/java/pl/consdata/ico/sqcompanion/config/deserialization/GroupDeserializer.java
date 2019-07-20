package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang.StringUtils;
import pl.consdata.ico.sqcompanion.config.model.GroupDefinition;

import java.io.IOException;

import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;

public class GroupDeserializer extends StdDeserializer<GroupDefinition> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultDeserializer;

    public GroupDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(GroupDefinition.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public GroupDefinition deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        GroupDefinition groupDefinition = (GroupDefinition) defaultDeserializer.deserialize(jsonParser, context);
        groupDefinition.setUuid(generateUuidIfRequired(groupDefinition.getUuid()));
        groupDefinition.setName(StringUtils.isBlank(groupDefinition.getName()) ? groupDefinition.getUuid() : groupDefinition.getName());
        return groupDefinition;
    }


    @Override
    public void resolve(DeserializationContext context) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(context);
    }
}
