package com.vzoom.simpleflow.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TokenBuffer;

import java.io.IOException;

public class Clones {
    private static final ObjectMapper OBJECT_MAPPER = Jsons.getObjectMapper();

    public static <T> T cloneObject(Object object, TypeReference<T> valueTypeRef) {
        try {
            TokenBuffer tokenBuffer = new TokenBuffer(OBJECT_MAPPER, false);
            OBJECT_MAPPER.writeValue(tokenBuffer, object);
            return OBJECT_MAPPER.readValue(tokenBuffer.asParser(), valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException("json转换错误", e);
        }
    }

}
