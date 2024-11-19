package team15.homelessconsuming.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert object to JSON string
    public static String objectToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    // Convert JSON string to object
    public static <T> T jsonToObject(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }
}