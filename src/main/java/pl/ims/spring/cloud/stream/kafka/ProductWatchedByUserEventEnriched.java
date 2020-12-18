package pl.ims.spring.cloud.stream.kafka;

import lombok.*;

/*
 * Created on 2020-12-18 16:14
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class ProductWatchedByUserEventEnriched {
    private ProductWatchedByUserEvent productWatchedByUserEvent;
    private Product product;
}
