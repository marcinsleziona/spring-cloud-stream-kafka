package pl.ims.spring.cloud.stream.kafka;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/*
 * Created on 2020-12-15 09:07
 */
public class JsonSerializer<T> implements Serializer<T> {

    @Override
    public byte[] serialize(String s, T t) {
        byte[] ret = null;
        try {
            ret = JsonObjectMapper.safeWrite(t).getBytes();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T t) {
        byte[] ret = null;
        try {
            ret = JsonObjectMapper.safeWrite(t).getBytes();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void close() {
    }

}
