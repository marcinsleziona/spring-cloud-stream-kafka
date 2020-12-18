package pl.ims.spring.cloud.stream.kafka.interfaces;

import lombok.*;

/*
 * Created on 2020-12-18 19:02
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class UsersPerCategory {
    private String category;
    private Long count;
}
