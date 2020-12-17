package pl.ims.spring.cloud.stream.kafka;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/*
 * Created on 2020-12-15 09:07
 */
public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> tClass;

    public JsonDeserializer() {
    }

    public JsonDeserializer(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T deserialize(String s, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }

}
