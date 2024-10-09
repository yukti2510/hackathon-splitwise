package hackathon.splitwise.convertor;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gauravlikhar
 * @project hackathon-splitwise
 */
@Converter
public class JsonToMapConverter implements AttributeConverter<Map, String> {
    private static ObjectMapper mapper;

    static {
        // To avoid instantiating ObjectMapper again and again.
        mapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(Map data) {
        if (null == data) {
            // You may return null if you prefer that style
            return "{}";
        }

        try {
            return mapper.writeValueAsString(data);

        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting map to JSON", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String s) {
        if (null == s) {
            // You may return null if you prefer that style
            return new HashMap<>();
        }

        try {
            return mapper.readValue(s, new TypeReference<Map<Object, Object>>() {
            });

        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to map", e);
        }
    }
}
