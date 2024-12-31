package com.raulo.literalura.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class FormatData implements IFormatData {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T format(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
