package pl.ims.spring.cloud.stream.kafka;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/*
 * Created on 2020-12-15 09:08
 */
public class UserFixture {
    public static User.UserBuilder randomBuilder() {
        return User.builder()
                .login(randomAlphabetic(10).toLowerCase()+"@"+randomAlphabetic(5).toLowerCase()+"."+randomAlphabetic(2).toLowerCase())
                .firstname(randomAlphabetic(1).toUpperCase()+randomAlphabetic(10).toLowerCase())
                .lastname(randomAlphabetic(1).toUpperCase()+randomAlphabetic(10).toLowerCase());
    }

    public static User random() {
        return randomBuilder().build();
    }


    public static List<User> randomList(int count) {
        return Stream.generate(UserFixture::random).limit(count).collect(Collectors.toList());
    }

}
