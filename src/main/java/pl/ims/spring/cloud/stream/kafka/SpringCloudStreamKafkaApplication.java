package pl.ims.spring.cloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ims.spring.cloud.stream.kafka.infrastructure.ProductRepository;
import pl.ims.spring.cloud.stream.kafka.infrastructure.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@SpringBootApplication
@Slf4j
public class SpringCloudStreamKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Bean
    public Function<KStream<String, User>, KStream<String, User>> usersKeySelection() {
        return input -> input.selectKey((key, value) -> value.getLogin());
    }

    @Bean
    public Consumer<KTable<String, User>> printUsers() {
        return input ->
                input
                        .toStream()
                        .foreach((key, value) -> {
                            System.out.println("UserK: " + key + " UserV: " + value);
                        });
    }

    @Bean
    public Function<KStream<Long, Product>, KStream<Long, Product>> productsKeySelection() {
        return input -> input.selectKey((key, value) -> value.getId());
    }

    @Bean
    public Consumer<KTable<Long, Product>> printProducts() {
        return input ->
                input
                        .toStream()
                        .foreach((key, value) -> {
                            System.out.println("ProductK: " + key + " ProductV: " + value);
                        });
    }

    @Bean
    public Supplier<ProductWatchedByUserEvent> pullProductWatchedByUserEvents() {
        return () ->
                ProductWatchedByUserEvent.builder()
                        .localDateTime(LocalDateTime.now())
                        .login(userRepository.findRandom().getLogin())
                        .productId(productRepository.findRandom().getId())
                        .build();
    }


    @Bean
    public Consumer<ProductWatchedByUserEvent> printProductWatchedByUserEvents() {
        return System.out::println;
    }

    @Bean
    public BiFunction<KStream<Long, ProductWatchedByUserEvent>, KTable<Long, Product>, KStream<String, Long>> countUsersByProductCategory() {
        return (productWatchedByUserEventStream, productsTable) -> productWatchedByUserEventStream
                .selectKey((key, value) -> value.getProductId())
                .leftJoin(productsTable,
                        (productWatchedByUserEventValue, productValue) -> new ProductWatchedByUserEventEnriched(productWatchedByUserEventValue, productValue),
                        Joined.with(Serdes.Long(), Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(ProductWatchedByUserEvent.class)), null)
                        )
                .map((key, value) -> new KeyValue<>(value.getProduct().getCategory().toString(), value))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(ProductWatchedByUserEventEnriched.class))))
                .count(Materialized.as(Stores.inMemoryKeyValueStore("count-store")))
                .toStream();
    }

    @Bean
    public Consumer<KStream<String, Long>> printCountUsersByProductCategory() {
        return input ->
                input
                        .foreach((key, value) -> {
                            System.out.println("Category: " + key + " Count: " + value);
                        });
    }


}
