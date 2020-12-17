package pl.ims.spring.cloud.stream.kafka;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/*
 * Created on 2020-12-15 09:07
 */
public class UserDeserializer implements Deserializer<User> {

    @Override
    public User deserialize(String s, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public User deserialize(String topic, Headers headers, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }

}
