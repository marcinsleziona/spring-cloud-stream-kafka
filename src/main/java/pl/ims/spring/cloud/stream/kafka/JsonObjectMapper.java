package pl.ims.spring.cloud.stream.kafka;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/*
 * Created by Marcin on 2018-03-01 15:06
 */
public class JsonObjectMapper {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    public static <T> String write(T value) throws JsonProcessingException {
        if (value == null) {
            return null;
        }
        return mapper.writeValueAsString(value);
    }

    public static <T> String safeWrite(T value) {
        try {
            return write(value);
        } catch (Exception e) {
            return "";
        }
    }

    public static <T> T read(String value, Class<T> type) throws IOException {
        return mapper.readValue(value, type);
    }

    public static <T> T read(InputStream inputStream, Class<T> type) throws IOException {
        return mapper.readValue(inputStream, type);
    }
}
