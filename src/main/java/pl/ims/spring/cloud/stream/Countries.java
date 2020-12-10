package pl.ims.spring.cloud.stream;

import lombok.*;

import java.util.List;

/*
 * Created on 2020-12-09 11:09
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Countries {
    private List<Country> countries;
}
