package pl.consdata.ico.sqcompanion.config.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import pl.consdata.ico.sqcompanion.config.model.Member;

import java.io.IOException;

import static pl.consdata.ico.sqcompanion.config.deserialization.DeserializationUtil.generateUuidIfRequired;

public class MemberDeserializer extends StdDeserializer<Member> implements ResolvableDeserializer {

    private final JsonDeserializer<?> defaultDeserializer;

    public MemberDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(Member.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public Member deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        Member member = (Member) defaultDeserializer.deserialize(jsonParser, context);
        member.setUuid(generateUuidIfRequired(member.getUuid()));
        return member;
    }


    @Override
    public void resolve(DeserializationContext context) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserializer).resolve(context);
    }
}
