package com.is442g1t4.gpa.utility;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonLongSerializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        String longData = jsonParser.getText();
        try {
            return Long.parseLong(longData);
        } catch (NumberFormatException e) {
            return null;
        }

    }
}
