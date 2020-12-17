package pl.ims.spring.cloud.stream.kafka;

import lombok.*;

import java.time.Instant;

/*
 * Created on 2020-12-15 10:03
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class ProductWatchedByUserEvent {
    private String login;
    private Long productId;
    private Instant instant;
}
