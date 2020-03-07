package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.Member;

import java.io.IOException;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.*;

public class MemberDeserializer extends StdDeserializer<Member> {

    public MemberDeserializer() {
        this(null);
    }

    protected MemberDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Member deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ObjectMapper mapper = new ObjectMapper();
        return Member.builder()
                .uuid(generateUuidIfRequired(node.get("uuid")))
                .firstName(textOfBlankNode(node.get("firstName"), EMPTY))
                .lastName(textOfBlankNode(node.get("lastName"), EMPTY))
                .sonarId(textOfBlankNode(node.get("sonarId"), EMPTY))
                .aliases(listOfNullable(node.get("aliases"), mapper))
                .mail(textOfBlankNode(node.get("mail"), EMPTY))
                .memberOf(listOfNullable(node.get("memberOf"), mapper))
                .build();
    }
}
