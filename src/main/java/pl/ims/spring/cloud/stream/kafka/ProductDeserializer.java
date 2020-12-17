package pl.ims.spring.cloud.stream.kafka;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/*
 * Created on 2020-12-15 09:07
 */
public class ProductDeserializer implements Deserializer<Product> {

    @Override
    public Product deserialize(String s, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Product deserialize(String topic, Headers headers, byte[] data) {
        try {
            return JsonObjectMapper.read(new String(data), Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }

}
