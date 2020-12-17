package pl.ims.spring.cloud.stream.kafka;

import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/*
 * Created on 2020-12-15 09:08
 */
public class ProductFixture {
    public static Product.ProductBuilder randomBuilder() {
        return Product.builder()
                .id(RandomUtils.nextLong(1, 100))
                .name(randomAlphabetic(1).toUpperCase()+randomAlphabetic(20).toLowerCase());
    }

    public static Product random() {
        return randomBuilder().build();
    }


    public static List<Product> randomList(int count) {
        return Stream.iterate(1, i -> i + 1)
                .limit(count)
                .map(integer -> randomBuilder().id((long)integer).build())
                .collect(Collectors.toList());
    }
}
