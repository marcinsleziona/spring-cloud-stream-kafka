package pl.ims.spring.cloud.stream.kafka.infrastructure;

import org.springframework.stereotype.Repository;
import pl.ims.spring.cloud.stream.kafka.Gender;
import pl.ims.spring.cloud.stream.kafka.User;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/*
 * Created on 2020-12-15 09:56
 */
@Repository
public class UserRepository {

    private User[] users;

    @PostConstruct
    public void init() {
        int count = 200;
        users = Stream.iterate(1, i -> i + 1)
                .limit(count)
                .map(integer -> User.builder()
                        .login(randomAlphabetic(10).toLowerCase()+integer+"@"+randomAlphabetic(5).toLowerCase() + "." + randomAlphabetic(2).toLowerCase())
                        .firstname(randomAlphabetic(1).toUpperCase() + randomAlphabetic(10).toLowerCase())
                        .lastname(randomAlphabetic(1).toUpperCase() + randomAlphabetic(10).toLowerCase())
                        .gender(RandomValue.random(Gender.values()))
                        .build())
                .toArray(User[]::new);
    }

    public User[] findAll() {
        return users;
    }

    public User findRandom() {
        return RandomValue.random(users);
    }
}
