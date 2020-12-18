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
public class Product {

    private Long id;
    private String name;
    private Double prize;
    private Category category;

}
