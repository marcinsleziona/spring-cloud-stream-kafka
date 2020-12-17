package pl.ims.spring.cloud.stream.kafka.infrastructure;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/*
 * Created by Marcin on 2018-03-01 15:48
 */
public class RandomValue {

    public static <T> T random(T[] values) {
        return values[RandomUtils.nextInt(0, values.length)];
    }

    public static <T> T random(List<T> values) {
        return values.get(RandomUtils.nextInt(0, values.size()));
    }
}
