package pl.ims.spring.cloud.stream;

import lombok.*;

import java.util.List;

/*
 * Created on 2020-12-01 08:30
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Country {
    private String id;
    private List<Link> links;
    private String name;
    private String name_es;
    private String name_it;
}
