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
import java.util.ArrayList;

import static java.util.Optional.ofNullable;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;

public class GroupDeserializer extends StdDeserializer<GroupDefinition> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultDeserializer;

    public GroupDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(GroupDefinition.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    public static void ensureParams(GroupDefinition groupDefinition) {
        groupDefinition.setGroups(ofNullable(groupDefinition.getGroups()).orElse(new ArrayList<>()));
        groupDefinition.setEvents(ofNullable(groupDefinition.getEvents()).orElse(new ArrayList<>()));
        groupDefinition.setProjectLinks(ofNullable(groupDefinition.getProjectLinks()).orElse(new ArrayList<>()));
        groupDefinition.setWebhooks(ofNullable(groupDefinition.getWebhooks()).orElse(new ArrayList<>()));
        groupDefinition.setMembers(ofNullable(groupDefinition.getMembers()).orElse(new ArrayList<>()));
    }

    @Override
    public GroupDefinition deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        GroupDefinition groupDefinition = (GroupDefinition) defaultDeserializer.deserialize(jsonParser, context);
        groupDefinition.setUuid(generateUuidIfRequired(groupDefinition.getUuid()));
        groupDefinition.setName(StringUtils.isBlank(groupDefinition.getName()) ? groupDefinition.getUuid() : groupDefinition.getName());
        ensureParams(groupDefinition);
        return groupDefinition;
    }

    @Override
    public void resolve(DeserializationContext context) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(context);
    }
}
