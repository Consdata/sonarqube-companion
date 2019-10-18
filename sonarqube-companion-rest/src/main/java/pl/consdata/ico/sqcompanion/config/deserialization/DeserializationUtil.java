package pl.consdata.ico.sqcompanion.config.deserialization;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@UtilityClass
public class DeserializationUtil {

    public static boolean isUuidBlank(JsonNode uuid) {
        return uuid == null || StringUtils.isBlank(uuid.asText()) || uuid.asText().equalsIgnoreCase("null");
    }

    public static String generateUuidIfRequired(JsonNode uuid) {
        return DeserializationUtil.isUuidBlank(uuid) ? UUID.randomUUID().toString() : uuid.asText();
    }

    public static String generateUuidIfRequired(String uuid) {
        return StringUtils.isBlank(uuid) ? UUID.randomUUID().toString() : uuid;
    }

    public static String textOfBlankNode(JsonNode node, String defaultValue) {
        if (node != null && isNotBlank(node.asText())) {
            return node.asText();
        } else {
            return defaultValue;
        }
    }

    public static List<String> listOfNullable(JsonNode node, ObjectMapper objectMapper) {
        if (node == null) {
            return new ArrayList<>();
        } else {
            return objectMapper.convertValue(node, List.class);
        }
    }

    public static Map<String, String> mapOfNullable(JsonNode node, ObjectMapper objectMapper) {
        if (node == null) {
            return new HashMap<>();
        } else {
            return objectMapper.convertValue(node, Map.class);
        }
    }
}
