package pl.ims.spring.cloud.stream.kafka.infrastructure;

import org.springframework.stereotype.Repository;
import pl.ims.spring.cloud.stream.kafka.Product;
import pl.ims.spring.cloud.stream.kafka.User;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/*
 * Created on 2020-12-15 09:56
 */
@Repository
public class ProductRepository {

    private Product[] products;

    @PostConstruct
    public void init() {
        int count = 100;
        products = Stream.iterate(1, i -> i + 1)
                .limit(count)
                .map(integer -> Product.builder()
                        .id((long)integer)
                        .name(randomAlphabetic(1).toUpperCase()+randomAlphabetic(20).toLowerCase())
                        .build())
                .toArray(Product[]::new);

    }

    public Product[] findAll() {
        return products;
    }

    public Product findRandom() {
        return RandomValue.random(products);
    }
}
