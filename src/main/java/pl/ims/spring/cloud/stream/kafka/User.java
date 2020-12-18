package pl.ims.spring.cloud.stream.kafka;

import lombok.*;

/*
 * Created on 2020-12-15 09:07
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class User {

    private String login;
    private String firstname;
    private String lastname;
    private Gender gender;

}
