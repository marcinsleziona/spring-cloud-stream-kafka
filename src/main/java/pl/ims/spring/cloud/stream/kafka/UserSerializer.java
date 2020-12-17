package pl.ims.spring.cloud.stream.kafka;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/*
 * Created on 2020-12-15 09:07
 */
public class UserSerializer implements Serializer<User> {

    @Override
    public byte[] serialize(String s, User user) {
        byte[] ret = null;
        try {
            ret = JsonObjectMapper.safeWrite(user).getBytes();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Headers headers, User user) {
        byte[] ret = null;
        try {
            ret = JsonObjectMapper.safeWrite(user).getBytes();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void close() {
    }

}
