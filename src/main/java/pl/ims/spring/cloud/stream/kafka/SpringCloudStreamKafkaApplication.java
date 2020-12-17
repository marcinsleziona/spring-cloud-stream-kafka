package pl.ims.spring.cloud.stream.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.ims.spring.cloud.stream.kafka.infrastructure.ProductRepository;
import pl.ims.spring.cloud.stream.kafka.infrastructure.UserRepository;

import java.time.Instant;
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
                        .instant(Instant.now())
                        .login(userRepository.findRandom().getLogin())
                        .productId(productRepository.findRandom().getId())
                        .build();
    }


    @Bean
    public Consumer<ProductWatchedByUserEvent> printProductWatchedByUserEvents() {
        return System.out::println;
    }


}
