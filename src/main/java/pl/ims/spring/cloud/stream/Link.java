package pl.ims.spring.cloud.stream;

import lombok.*;

/*
 * Created on 2020-12-09 11:04
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Link {
    private String href;
    private String rel;
    private String type;
}
